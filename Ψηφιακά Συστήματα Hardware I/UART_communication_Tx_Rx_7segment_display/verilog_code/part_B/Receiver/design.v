// Code your design here
`include "Baud_Rate_Generator"
`include "Data_Reception"
`include "Parity_Generator"
`include "Data_Extraction"
`include "Parity_Comparison"
`include "Validation_Control"
`include "Framing_Control"

`timescale 1ns/1ps

module UART_Receiver(
  input RxD,
  input Rx_EN,
  input [2:0] baud_select,
  input Clk,
  input reset,
  output wire Rx_PERROR,
  output wire Rx_FERROR, 
  output wire Rx_VALID,
  output wire [7:0] Rx_DATA
  
);
  
  wire Rx_Sample_ENABLE;
  wire [10:0] packet;
  wire start_bit_sync;
  wire original_parity_bit;
  wire [7:0] Data;
  wire parity_bit;
  
  wire packet_completion;
  
  baud_rate_generator baud_rate_generator_instance(Clk, reset, baud_select, Rx_Sample_ENABLE);
  
  data_reception data_reception_instance(RxD, Rx_EN, Rx_Sample_ENABLE, packet, start_bit_sync, packet_completion);
  
  data_extraction data_extraction_instance(packet,packet_completion, original_parity_bit, Data);
  
  parity_generator parity_generator_instance(Data, parity_bit);
  
  parity_comparison parity_comparison_instance(original_parity_bit, parity_bit, Rx_PERROR);
  
  framing_control framing_control_instance(start_bit_sync, Rx_FERROR);
  
  validation_control validation_control_instance(Rx_FERROR, Rx_PERROR, Data, Rx_DATA, Rx_VALID);
  
endmodule