`include "UART_Transmitter"
`include "UART_Receiver"
`include "Channel"
`include "Parity_Generator"

`timescale 1ns/1ps
// Code your design here
module UART(
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
  	output wire Rx_PERROR,
    output wire Rx_FERROR,
    output wire Rx_VALID,
    output wire [7:0] Rx_DATA 
  
);
  
  wire TxD;
  wire RxD;
  
  channel channel_instance(TxD, RxD);
  
  UART_Transmitter UART_Transmitter_Instance(Clk, reset, Tx_WR, Tx_EN, Tx_DATA,
                                             baud_select, Tx_BUSY, TxD);
  
  
  
  UART_Receiver tb_uart_receiver_instance(RxD, Rx_EN, baud_select, Clk, reset,
                                         Rx_PERROR, Rx_FERROR,Rx_VALID,Rx_DATA);
  
endmodule