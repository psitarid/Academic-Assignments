`timescale 1ns/1ps

module Rx_data_extraction(
  
  input [10:0] packet,
  input packet_completion,
  
  output reg original_parity_bit = 0, //The parity bit which was calculated during the transmission.
  output reg [7:0] Data = 0
  
);
  
  integer i = 0;
  always @ (posedge packet_completion) 
    begin
        original_parity_bit <= packet[9];
        for(i = 0 ; i<8 ; i = i + 1)
           begin
             Data[i] = packet[i + 1]; // Data extraction.
           end      
    end
endmodule