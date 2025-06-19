int LEDpin =7; // digital output
int LEDbrightness ; // to map the values
int sensorValue ; // to read the sensor

void setup() {
  Serial.begin(9600);   // initialize serial communication at 9600 bits per second
  pinMode(LEDpin, OUTPUT);  //Serial monitor
}
void loop() {
  sensorValue = analogRead(A1);  // read the input on analog pin 0
  Serial.println(sensorValue); // print out the value you read
  LEDbrightness = map(sensorValue , 0 , 1023 , 0 , 255) ; //mapping
  analogWrite(LEDpin, LEDbrightness) ; //write to digital output
  delay(100); // wait 0.1 s before looping
 }
