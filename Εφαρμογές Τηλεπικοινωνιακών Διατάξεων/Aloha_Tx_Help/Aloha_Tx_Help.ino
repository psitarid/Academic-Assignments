// Communication things
// #include <SPI.h>
#include <RF22.h>
#include <RF22Router.h>

//PPG things
#define USE_ARDUINO_INTERRUPTS true
#include <PulseSensorPlayground.h>

//Gyro things
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>

// Communication setup
#define MY_ADDRESS 12
#define DESTINATION_ADDRESS 11
RF22Router rf22(MY_ADDRESS);

//PPG things
const int OUTPUT_TYPE = SERIAL_PLOTTER;
const int PULSE_INPUT = A0;
const int PULSE_BLINK = LED_BUILTIN; 
const int PULSE_FADE = 5;
const int THRESHOLD = 550;   // Adjust this number to avoid noise when idle
PulseSensorPlayground pulseSensor;
int heart_beat = 0;

//Gyro things
Adafruit_MPU6050 mpu;
int sensorVal = 10;
long randNumber;
boolean successful_packet = false;
int max_delay=20000;
float a_x;
float a_y;
float a_z;
float temp;

void setup() {
  Serial.begin(9600);
  
  if (!rf22.init())
    Serial.println("RF22 init failed");
  // Defaults after init are 434.0MHz, 0.05MHz AFC pull-in, modulation FSK_Rb2_4Fd36
  
  if (!rf22.setFrequency(430.0))
    Serial.println("setFrequency Fail");
  
  rf22.setTxPower(RF22_TXPOW_20DBM);
  //1,2,5,8,11,14,17,20 DBM
  
  rf22.setModemConfig(RF22::GFSK_Rb125Fd125);
  //modulation

  // Manually define the routes for this network
  rf22.addRouteTo(DESTINATION_ADDRESS, DESTINATION_ADDRESS);
  
  sensorVal = 15;
  randomSeed(sensorVal);// (μία μόνο φορά μέσα στην setup)

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
    /*
       PulseSensor initialization failed,
       likely because our particular Arduino platform interrupts
       aren't supported yet.

       If your Sketch hangs here, try PulseSensor_BPM_Alternative.ino,
       which doesn't use interrupts.
    */
    for(;;) {
      // Flash the led to show things didn't work.
      digitalWrite(PULSE_BLINK, LOW);
      delay(50);
      digitalWrite(PULSE_BLINK, HIGH);
      delay(50);
    }
  }

  //Gyro things
  // Try to initialize!
  if (!mpu.begin()) {
    Serial.println("Failed to find MPU6050 chip");
    while (1) {
      delay(10);
    }
  }
  Serial.println("MPU6050 Found!");

  // set accelerometer range to +-8G
  mpu.setAccelerometerRange(MPU6050_RANGE_8_G);

  // set gyro range to +- 500 deg/s
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);

  // set filter bandwidth to 21 Hz
  mpu.setFilterBandwidth(MPU6050_BAND_21_HZ);
  
  delay(100);
}

void loop() {

  /*
     Wait a bit.
     We don't output every sample, because our baud rate
     won't support that much I/O.
  */
  delay(20);

  //PPG things
  heart_beat = pulseSensor.getBeatsPerMinute();
  heart_beat = heart_beat;
  Serial.print("Heart beat: ");
  Serial.print(heart_beat);
  Serial.println("BPM");
//  if (pulseSensor.sawStartOfBeat()) {
//    heart_beat = pulseSensor.getBeatsPerMinute();
//    Serial.print(heart_beat);
//  }

  //Gyro things
  /* Get new sensor events with the readings */
  sensors_event_t a, g, temp;
  mpu.getEvent(&a, &g, &temp);
  
  /* Print out the values */
  Serial.print("Acceleration X: ");
  a_x = a.acceleration.x * 1000;
  Serial.print(a_x);
  Serial.print(", Y: ");
  a_y = a.acceleration.y * 1000;
  Serial.print(a_y);
  Serial.print(", Z: ");
  a_z = a.acceleration.z * 1000;
  Serial.print(a_z);
  Serial.println(" m/s^2");

  int xA = static_cast<int>(a_x);
  Serial.print(xA);
  Serial.print("  ");
  int yA = static_cast<int>(a_y);
  Serial.print(yA);
  Serial.print("  ");
  int zA = static_cast<int>(a_z);
  Serial.println(zA);
  
  char data_read[RF22_ROUTER_MAX_MESSAGE_LEN];
  uint8_t data_send[RF22_ROUTER_MAX_MESSAGE_LEN];
  memset(data_read, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
  memset(data_send, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);

  // SEND HEARTBEATS
  sprintf(data_read, "%d", heart_beat);
  data_read[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
  memcpy(data_send, data_read, RF22_ROUTER_MAX_MESSAGE_LEN);

  successful_packet = false;
  while (!successful_packet)
  {

    if (rf22.sendtoWait(data_send, sizeof(data_send), DESTINATION_ADDRESS) != RF22_ROUTER_ERROR_NONE)
    {
      Serial.println("sendtoWait failed");
      randNumber=random(200,max_delay);
//      Serial.println(randNumber);
//      delay(randNumber);
    }
    else
    {
      successful_packet = true;
      Serial.println("sendtoWait Succesful");
    }
  }

  // SEND X AXIS ACCELERATION
  sprintf(data_read, "%d", xA);
  data_read[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
  memcpy(data_send, data_read, RF22_ROUTER_MAX_MESSAGE_LEN);

  successful_packet = false;
  while (!successful_packet)
  {

    if (rf22.sendtoWait(data_send, sizeof(data_send), DESTINATION_ADDRESS) != RF22_ROUTER_ERROR_NONE)
    {
      Serial.println("sendtoWait failed");
      randNumber=random(200,max_delay);
//      Serial.println(randNumber);
//      delay(randNumber);
    }
    else
    {
      successful_packet = true;
      Serial.println("sendtoWait Succesful");
    }
  }

  // SEND Y AXIS ACCELERATION
  sprintf(data_read, "%d", yA);
  data_read[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
  memcpy(data_send, data_read, RF22_ROUTER_MAX_MESSAGE_LEN);

  successful_packet = false;
  while (!successful_packet)
  {

    if (rf22.sendtoWait(data_send, sizeof(data_send), DESTINATION_ADDRESS) != RF22_ROUTER_ERROR_NONE)
    {
      Serial.println("sendtoWait failed");
      randNumber=random(200,max_delay);
//      Serial.println(randNumber);
//      delay(randNumber);
    }
    else
    {
      successful_packet = true;
      Serial.println("sendtoWait Succesful");
    }
  }

  // SEND Z AXIS ACCELERATION
  sprintf(data_read, "%d", zA);
  data_read[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
  memcpy(data_send, data_read, RF22_ROUTER_MAX_MESSAGE_LEN);

  successful_packet = false;
  while (!successful_packet)
  {

    if (rf22.sendtoWait(data_send, sizeof(data_send), DESTINATION_ADDRESS) != RF22_ROUTER_ERROR_NONE)
    {
      Serial.println("sendtoWait failed");
      randNumber=random(200,max_delay);
//      Serial.println(randNumber);
//      delay(randNumber);
    }
    else
    {
      successful_packet = true;
      Serial.println("sendtoWait Succesful");
    }
  }
//  
  delay(1000);
}
