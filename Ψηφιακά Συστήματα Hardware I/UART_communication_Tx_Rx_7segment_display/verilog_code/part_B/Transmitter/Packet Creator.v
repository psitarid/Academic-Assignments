`timescale 1ns/1ps

module packet_creator(
  input parity_bit,
  input [7:0] Data,
  output reg [10:0] packet = 0
);
  
  integer i;
  always@(Data or parity_bit)
    begin
      packet = 0; // Fill the packet with zeroes
      packet[0] <= 0; // Start bit
      
      for(i=0; i<8 ; i=i+1)
        packet[i+1] <= Data[i]; // Insert data
      
      packet[9] = parity_bit; // Parity bit
      packet[10] = 1; // Stop bit

    end
  
endmodule