// #include <SPI.h>
#include <RF22.h>
#include <RF22Router.h>
#include <dht11.h>
#include <stdio.h>

#define DHT11PIN 4
#define MY_ADDRESS 101
#define DESTINATION_ADDRESS 15
RF22Router rf22(MY_ADDRESS);

dht11 DHT11;
int sensorVal = 10;
long randNumber; //network card
boolean successful_packet = false;
int max_delay=500; //network card
const int PIEZO_PIN = A1; // Piezo output
int LEDpin =7; // digital output
int LEDbrightness; // to map the values
int sensorValuePressure ; // to read the sensor
float value_humidity;
float value_temperature;

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
//  sensorValuePressure = 15;
//  Serial.println(sensorValuePressure); // print out the value you read
 
  ////////////////////////////////////////////////////////////////////////////////////////
  
  Serial.print(piezoV);
  Serial.print(",");
  Serial.print(value_humidity);
  Serial.print(",");
  Serial.print(value_temperature);
  Serial.print(",");
  Serial.print(sensorValuePressure);
  Serial.println(",");
  
  char data_read[RF22_ROUTER_MAX_MESSAGE_LEN];
  uint8_t data_send[RF22_ROUTER_MAX_MESSAGE_LEN];
  memset(data_read, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
  memset(data_send, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
//  strcpy(data_read, data);
  sprintf(data_read, "%d %d %d %d", (int)piezoV, (int)DHT11.humidity, (int)DHT11.temperature, (int)sensorValuePressure);
//  Serial.println(data_read);
  data_read[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
  memcpy(data_send, data_read, RF22_ROUTER_MAX_MESSAGE_LEN);

  successful_packet = false;
  while (!successful_packet)
  {

    if (rf22.sendtoWait(data_send, sizeof(data_send), DESTINATION_ADDRESS) != RF22_ROUTER_ERROR_NONE)
    {
//    Serial.println("sendtoWait failed");
      randNumber=random(100,max_delay);
//    Serial.println(randNumber);
      delay(randNumber);
    }
    else
    {
      successful_packet = true;
//    Serial.println("sendtoWait Succesful");
      delay(30);
    }
  }
  
}
