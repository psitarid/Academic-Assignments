`timescale 1ns/1ps

module bcd_control(
  input [3:0] digit1, //right digit
  input [3:0] digit2, // tens
  input [3:0] digit3, // hundreds
  input [3:0] digit4, // thousands
  input [3:0] anode2,
  output reg[3:0] ONE_DIGIT = 0 // choose which digit is to be displayed
);
  
  
  always @ (anode2)
  begin
    case(anode2)
	  4'b0111:
		  ONE_DIGIT = digit1; //digit 1 value (right digit)
	  4'b1011:
		  ONE_DIGIT = digit2; // digit 2 value
	  4'b1101:
		  ONE_DIGIT = digit3; // digit 3 value
	  4'b1110:
		  ONE_DIGIT = digit4; // digit 4 value (left digit)
	endcase
  end
  
endmodule