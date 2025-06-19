#include <RF22.h>
#include <RF22Router.h>
#include <string.h>
#include <stdlib.h>

#define MY_ADDRESS 29 // define my unique address

// define who I can talk to
#define NODE_ADDRESS_1 37
#define NODE_ADDRESS_2 15
#define NODE_ADDRESS_3 64

long randNumber; //network card
int max_delay=1000;
boolean successful_packet = false;
int class_id = 5;


RF22Router rf22(MY_ADDRESS);


void setup() {
  Serial.begin(9600);
  if (!rf22.init())
    Serial.println("RF22 init failed");
  // Defaults after init are 434.0MHz, 0.05MHz AFC pull-in, modulation FSK_Rb2_4Fd36
  if (!rf22.setFrequency(443.0))
    Serial.println("setFrequency Fail");
  rf22.setTxPower(RF22_TXPOW_20DBM);
  //1,2,5,8,11,14,17,20 DBM
  rf22.setModemConfig(RF22::GFSK_Rb125Fd125);
  //modulation

  // Manually define the routes for this network
  rf22.addRouteTo(NODE_ADDRESS_1, NODE_ADDRESS_1);
  rf22.addRouteTo(NODE_ADDRESS_2, NODE_ADDRESS_2);
  rf22.addRouteTo(NODE_ADDRESS_3, NODE_ADDRESS_3);


}

void loop() {
  uint8_t buf[RF22_ROUTER_MAX_MESSAGE_LEN];
  char incoming[RF22_ROUTER_MAX_MESSAGE_LEN];
  memset(buf, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
  memset(incoming, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
  uint8_t len = sizeof(buf);
  
  uint8_t from;
  if (rf22.recvfromAck(buf, &len, &from))
  {
    buf[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
    memcpy(incoming, buf, RF22_ROUTER_MAX_MESSAGE_LEN);
    //Serial.print("got request from : ");
    Serial.println(from, DEC);
    //Serial.println(received_value);
    //   delay(1000);
  
  if(strlen(incoming) > 6){                 //check if the incoming packet includes the vibration, humidity, temperature and pressure. 
    char* token;
    token = strtok(incoming, " ");          //temporary variable to store the differend values that the incoming packet includes.

    int vibration_value = atoi(token);
    token = strtok(NULL, " ");
    int humidity_value = atoi(token);
    token = strtok(NULL, " ");
    int temperature_value = atoi(token);
    token = strtok(NULL, " ");
    int pressure_value = atoi(token);
  
    Serial.print(vibration_value);
    Serial.print(",");
    Serial.print(humidity_value);
    Serial.print(",");
    Serial.print(temperature_value);
    Serial.print(",");
    Serial.print(pressure_value);
    Serial.print(",");
  }
  else if(incoming < 6){                    //check if the incoming packet includes only the heart_rate_value
    int heart_rate_value = atoi(incoming);
    if(heart_rate_value > 100 or heart_rate_value< 40){
      char data_read[RF22_ROUTER_MAX_MESSAGE_LEN];
      uint8_t data_send[RF22_ROUTER_MAX_MESSAGE_LEN];
      memset(data_read, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
      memset(data_send, '\0', RF22_ROUTER_MAX_MESSAGE_LEN);
//  strcpy(data_read, data);
      sprintf(data_read, "%d", class_id);               //Send class ID to the school medics.
      data_read[RF22_ROUTER_MAX_MESSAGE_LEN - 1] = '\0';
      memcpy(data_send, data_read, RF22_ROUTER_MAX_MESSAGE_LEN);
      if (rf22.sendtoWait(data_send, sizeof(data_send), NODE_ADDRESS_3) != RF22_ROUTER_ERROR_NONE){         //start communication with NODE_ADDRESS_3
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


  
  }

}
