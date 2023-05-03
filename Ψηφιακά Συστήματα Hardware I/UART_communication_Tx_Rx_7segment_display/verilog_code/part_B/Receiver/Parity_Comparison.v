`timescale 1ns/1ps
 
module parity_comparison(
  input original_parity_bit,
  input parity_bit,
  output reg Rx_PERROR = 0
);
  
  always @ (original_parity_bit or parity_bit)
  begin
    if (original_parity_bit == parity_bit)
    begin
      Rx_PERROR <= 0; // No error.
    end
    
    else Rx_PERROR <=1; // Something went wrong
  end
    
  
endmodule
