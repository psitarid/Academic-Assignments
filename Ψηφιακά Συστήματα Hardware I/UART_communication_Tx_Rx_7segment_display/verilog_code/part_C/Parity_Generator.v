`timescale 1ns/1ps

module parity_generator(
  input [7:0] Data ,
  output reg parity_bit = 0
);
  integer i;
  integer ones;
  // When Number of data equal to 1 is odd, parity_bit = 1
  always@(Data)
    begin
      ones = 0; // Keeps track of number of 1s in Data
      for(i=0; i<8; i = i+1)
        begin
          if (Data[i] == 1)
      ones = ones + 1;
        end
     
     
      if(ones % 2 == 1)
        begin
    parity_bit<=1;
        end
   else begin
    parity_bit<=0;
      end
    end
endmodule