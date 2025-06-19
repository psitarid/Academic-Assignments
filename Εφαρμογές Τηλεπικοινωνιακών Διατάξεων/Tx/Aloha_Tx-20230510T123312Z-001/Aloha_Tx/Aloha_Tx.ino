// #include <SPI.h>
#include <RF22.h>
#include <RF22Router.h>
#include <dht11.h>
#include <stdio.h>
#include <PulseSensorPlayground.h>     // Includes the PulseSensorPlayground Library. 

#define USE_ARDUINO_INTERRUPTS true    // Set-up low-level interrupts for most acurate BPM math.
#define DHT11PIN 4
#define MY_ADDRESS 37
#define DESTINATION_ADDRESS 29
RF22Router rf22(MY_ADDRESS);

dht11 DHT11;
int sensorVal = 10;
long randNumber; //network card
boolean successful_packet = false;
int max_delay=1000; //network card
const int PIEZO_PIN = A1; // Piezo output
int LEDpin =7; // digital output
int LEDbrightness; // to map the values
int sensorValuePressure ; // to read the sensor
float value_humidity;
float value_temperature;
const int OUTPUT_TYPE = SERIAL_PLOTTER;
const int PULSE_INPUT = A0;
const int PULSE_BLINK = LED_BUILTIN; 
const int PULSE_FADE = 5;
const int THRESHOLD = 550;   // Adjust this number to avoid noise when idle
PulseSensorPlayground pulseSensor;
int heart_beat = 0;

void setup() 
{
  
  Serial.begin(9600);

  if (!rf22.init())
    Serial.println("RF22 init failed");
  // Defaults after init are 434.0MHz, 0.05MHz AFC pull-in, modulation FSK_Rb2_4Fd36
  if (!rf22.setFrequency(443.0))
    Serial.println("setFrequency Fail");
  rf22.setTxPower(RF22_TXPOW_20DBM);
  //1,2,5,8,11,14,17,20 DBM
  //rf22.setModemConfig(RF22::OOK_Rb40Bw335  );
  rf22.setModemConfig(RF22::GFSK_Rb125Fd125);
  //modulation

  // Manually define the routes for this network
  rf22.addRouteTo(DESTINATION_ADDRESS, DESTINATION_ADDRESS);

  pinMode(LEDpin, OUTPUT);  //Serial monitor

  //PPG things
  // Configure the PulseSensor manager.
  pulseSensor.analogInput(PULSE_INPUT);
  pulseSensor.blinkOnPulse(PULSE_BLINK);
  pulseSensor.fadeOnPulse(PULSE_FADE);
  pulseSensor.setSerial(Serial);
  pulseSensor.setOutputType(OUTPUT_TYPE);
  pulseSensor.setThreshold(THRESHOLD);

  // Now that everything is ready, start reading the PulseSensor signal.
  if (!pulseSensor.begin()) {
    for(;;) {
      // Flash the led to show things didn't work.
      digitalWrite(PULSE_BLINK, LOW);
      delay(50);
      digitalWrite(PULSE_BLINK, HIGH);
      delay(50);
    }
  }
}

void loop() 
{
  // Read Piezo ADC value in, and convert it to a voltage
  int piezoADC = analogRead(PIEZO_PIN);
  float piezoV = (piezoADC / 1023.0 * 5.0) * 1000;
//  Serial.print("piezo: ");
//  Serial.println(piezoV); //Print the voltage. 

  //////////////////////////////////////////////////////////////////////////////////////
  int chk = DHT11.read(DHT11PIN);

//  Serial.print("Humidity (%): ");
  value_humidity = (float)DHT11.humidity;
//  Serial.println(value_humidity, 2);
  
//  Serial.print("Temperature  (C): ");
  value_temperature = (float)DHT11.temperature;
//  Serial.println(value_temperature, 2);
  
  //////////////////////////////////////////////////////////////////////////////////////
  
  sensorValuePressure = analogRead(A4); // read the input on analog pin 0
//  Serial.println(sensorValuePressure); // print out the value you read
 
  ////////////////////////////////////////////////////////////////////////////////////////

  //PPG things
  heart_beat = pulseSensor.getBeatsPerMinute();
//  Serial.print("Heart beat: ");
//  Serial.print(heart_beat);
//  Serial.println("BPM");

  /////////////////////////////////////////////////////////////////////////////////////////
  
  Serial.print(piezoV);
  Serial.print(",");
  Serial.print(value_humidity);
  Serial.print(",");
  Serial.print(value_temperature);
  Serial.print(",");
  Serial.print(sensorValuePressure);
  Serial.print(",");
  Serial.print(heart_beat);
  Serial.println(",");
  
  char data_read[RF22_ROUTER_MAX_MESSAGE_LEN];
  uint8_t data_send[RF22_ROUTER_MAX_MESSAGE_LEN];
  memset(data_read, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
  memset(data_send, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
//  strcpy(data_read, data);
  sprintf(data_read, "%d %d %d %d %d", (int)piezoV, (int)DHT11.humidity, (int)DHT11.temperature, (int)sensorValuePressure, (int)heart_beat);
//  Serial.println(data_read);
  data_read[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
  memcpy(data_send, data_read, RF22_ROUTER_MAX_MESSAGE_LEN);

  successful_packet = false;
  while (!successful_packet)
  {

    if (rf22.sendtoWait(data_send, sizeof(data_send), DESTINATION_ADDRESS) != RF22_ROUTER_ERROR_NONE)
    {
//    Serial.println("sendtoWait failed");
      randNumber=random(200,max_delay);
//    Serial.println(randNumber);
      delay(randNumber);
    }
    else
    {
      successful_packet = true;
//    Serial.println("sendtoWait Succesful");
    }
  }
  
}
