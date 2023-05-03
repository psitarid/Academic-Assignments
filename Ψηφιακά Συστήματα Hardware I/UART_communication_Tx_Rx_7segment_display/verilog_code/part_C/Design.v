`include "UART_Transmitter"
`include "UART_Receiver"
`include "Channel"
`include "Parity_Generator"
`include "Digit_Producer"
`include "Segment7"

`timescale 1ns/1ps
 
module UART_Segment7(
    input Clk,
    input reset,
    input [2:0] baud_select,
    
  	//transmitter side
  	input Tx_WR,
  	input Tx_EN,
  	input [7:0] Tx_DATA,
  
  	//Receiver side
  	input Rx_EN,
  
    //transmitter side
    output wire Tx_BUSY,
  
    //Receiver side

    output wire Rx_VALID,
    
  
  //7 Segment Display side
  
  output wire [6:0] Led_Disp,
  output wire [3:0] anode

);
  
  
  wire Rx_FERROR;
  wire Rx_PERROR;
  wire [7:0] Rx_DATA;
  wire [3:0] digit1;
  wire [3:0] digit2;
  wire [3:0] digit3;
  wire [3:0] digit4;
  
  wire TxD;
  wire RxD;
  
  digit_producer digit_producer_instance(Rx_DATA, Rx_FERROR, Rx_PERROR, digit1 , digit2, digit3, digit4);
  
  TOP TOP_instance(digit1, digit2, digit3, digit4, Clk, reset, Led_Disp, anode);
  
  channel channel_instance(TxD, RxD);
  
  UART_Transmitter UART_Transmitter_Instance(Clk, reset, Tx_WR, Tx_EN, Tx_DATA,
                                             baud_select, Tx_BUSY, TxD);
  
  
  
  UART_Receiver tb_uart_receiver_instance(RxD, Rx_EN, baud_select, Clk, reset,
                                         Rx_PERROR, Rx_FERROR,Rx_VALID,Rx_DATA);
  
endmodule