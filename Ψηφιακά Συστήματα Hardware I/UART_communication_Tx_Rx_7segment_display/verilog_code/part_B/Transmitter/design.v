`include "Baud_Rate_Generator"
`include "Data_Reception"
`include "Parity_Generator"
`include "Packet_Creator"
`include "Transmission"

// Code your design here
`timescale 1ns/1ps
module UART_Transmitter(
	input Clk,
  	input reset,
  	input Tx_WR,
  	input Tx_EN,
    input [7:0] Tx_DATA,
    input [2:0] baud_select,
	output wire Tx_BUSY,
  	output wire TxD
);
  
  wire [7:0] Data;
  wire parity_bit;
  wire [10:0] packet;
  wire Tx_Sample_ENABLE;
  
  baud_rate_generator baud_rate_generator_instance(Clk, reset, baud_select, Tx_Sample_ENABLE);
  data_reception data_reception_instance(Tx_EN, Tx_WR, Tx_DATA, Data);
  parity_generator parity_generator_instance(Data, parity_bit);
  packet_creator packet_creator_instance(parity_bit, Data, packet);
  transmission transmission_instance(packet, Tx_Sample_ENABLE, TxD, Tx_BUSY);
  
endmodule