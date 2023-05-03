// Code your design here
`include "Rx_Baud_Rate_Generator"
`include "Rx_Data_Reception"
//`include "Parity_Generator"
`include "Rx_Data_Extraction"
`include "Rx_Parity_Comparison"
`include "Rx_Validation_Control"
`include "Rx_Framing_Control"

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
  
  Rx_baud_rate_generator Rx_baud_rate_generator_instance(Clk, reset, baud_select, Rx_Sample_ENABLE);
  
  Rx_data_reception Rx_data_reception_instance(RxD, Rx_EN, Rx_Sample_ENABLE, packet, start_bit_sync, packet_completion);
  
  Rx_data_extraction Rx_data_extraction_instance(packet,packet_completion, original_parity_bit, Data);
  
  parity_generator Rx_parity_generator_instance(Data, parity_bit);
  
  Rx_parity_comparison Rx_parity_comparison_instance(original_parity_bit, parity_bit, Rx_PERROR);
  
  Rx_framing_control Rx_framing_control_instance(start_bit_sync, Rx_FERROR);
  
  Rx_validation_control Rx_validation_control_instance(Rx_FERROR, Rx_PERROR, Data, Rx_DATA, Rx_VALID);
  
endmodule