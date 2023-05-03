
`timescale 1ns/1ps

module data_reception(
  input Tx_EN,
  input Tx_WR,
  input [7:0] Tx_DATA,
  output reg [7:0] Data = 0
);
  
  
  always@(Tx_WR)
    begin
      if (Tx_EN == 1 && Tx_WR == 1)
        begin
         Data <= Tx_DATA;
      end
    end
 
endmodule
    