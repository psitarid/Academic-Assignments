`include "Tx_Baud_Rate_Generator"
`include "Tx_Data_Reception"
`include "Tx_Packet_Creator"
`include "Tx_Transmission"
//`include "Parity_Generator"
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
 
  Tx_baud_rate_generator Tx_baud_rate_generator_instance(Clk, reset, baud_select, Tx_Sample_ENABLE);
  Tx_data_reception Tx_data_reception_instance(Tx_EN, Tx_WR, Tx_DATA, Data);
  parity_generator Tx_parity_generator_instance(Data, parity_bit);
  Tx_packet_creator Tx_packet_creator_instance(parity_bit, Data, packet);
  Tx_transmission Tx_transmission_instance(packet, Tx_Sample_ENABLE, TxD, Tx_BUSY);
 
endmodule