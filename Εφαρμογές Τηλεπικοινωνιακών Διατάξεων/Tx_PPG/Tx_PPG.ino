// #include <SPI.h>
#include <RF22.h>
#include <RF22Router.h>
#define MY_ADDRESS 100
#define DESTINATION_ADDRESS 15
RF22Router rf22(MY_ADDRESS);

#define USE_ARDUINO_INTERRUPTS true
#include <PulseSensorPlayground.h>

long randNumber;
boolean successful_packet = false;
int max_delay=1000;

//PPG things
const int OUTPUT_TYPE = SERIAL_PLOTTER;
const int PULSE_INPUT = A0;
const int PULSE_BLINK = LED_BUILTIN; 
const int PULSE_FADE = 5;
const int THRESHOLD = 550;   // Adjust this number to avoid noise when idle
PulseSensorPlayground pulseSensor;
int heart_beat;

void setup() {
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
      Serial.println("Error");
    }
  }
}

void loop() {

  //PPG things
  heart_beat = pulseSensor.getBeatsPerMinute();
//  Serial.print("Heart beat: ");
  Serial.println(heart_beat);
//  Serial.println("BPM");
  
  char data_read[RF22_ROUTER_MAX_MESSAGE_LEN];
  uint8_t data_send[RF22_ROUTER_MAX_MESSAGE_LEN];
  memset(data_read, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
  memset(data_send, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
  sprintf(data_read, "%d", (int)heart_beat);
  data_read[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
  memcpy(data_send, data_read, RF22_ROUTER_MAX_MESSAGE_LEN);

  successful_packet = false;
  while (!successful_packet)
  {

    if (rf22.sendtoWait(data_send, sizeof(data_send), DESTINATION_ADDRESS) != RF22_ROUTER_ERROR_NONE)
    {
//      Serial.println("sendtoWait failed");
//      randNumber=random(200,max_delay);
//      Serial.println(randNumber);
//      delay(randNumber);
    }
    else
    {
      successful_packet = true;
//      Serial.println("sendtoWait Succesful");
      delay(5000);
    }
  }
}
