// Unsigned 8-bit adder
module Add1(x, y, sum);  
   input logic  [7:0] x;  
   input logic  [7:0]	y;  
   output logic [7:0]	sum;  

   assign sum = x + y;  
endmodule // Add1

// Unsigned 8-bit adder with carry out. 
module Add2(x, y, sum, co);  
   input logic  [7:0] x;  
   input logic  [7:0]	y;  
   output logic [7:0]	sum;  
   output logic	co;  

   logic [8:0]	tmp; 

   assign tmp = x + y;  
   assign sum = tmp [7:0];  
   assign co  = tmp [8];  
endmodule // Add2

// unsigned 8-bit Add with carry in and carry out. 
module Add3(x, y, ci, sum, co);  
   input logic        ci;  
   input logic  [7:0] x;  
   input logic  [7:0] y;  
   output logic [7:0]	sum;  
   output logic	co;  

   logic [8:0]	tmp;  

   assign tmp = x + y + ci;  
   assign sum = tmp [7:0];  
   assign co  = tmp [8];  
endmodule // Add3

// Unsigned 8-bit subtractor. 
module Sub1 (x, y, res);  
   input logic  [7:0] x;  
   input logic  [7:0]	y;  
   output logic [7:0]	res; 
 
   assign res = x - y;  
endmodule // Sub1

// Unsigned 8-bit adder/subtractor instantiating modules Add1 and Sub1
module AddSub1(op, x, y, res);  
   input logic        op;  
   input logic  [7:0] x;  
   input logic  [7:0] y;  
   output logic [7:0]	res;  

   logic [7:0] sum;   
   Add1 Add1_inst (x, y, sum);

   logic [7:0] sub;   
   Sub1 Sub1_inst (x, y, sub);

   assign res = (op == 1'b0) ? sum : sub;
endmodule // AddSub1

// Unsigned 8-bit adder/subtractor
module AddSub2(op, x, y, res);  
   input  logic         op;  
   input  logic  [7:0]	x;  
   input  logic  [7:0]  y;  
   output logic  [7:0]  res;  

   logic [7:0]		sum, sub;

   assign sum = x + y;
   assign sub = x - y;
   
   assign res = (op == 1'b0) ? sum : sub;
   
endmodule // AddSub2

// Unsigned 8-bit adder/subtractor with always block
/* Later ...
module AddSub3(op, x, y, res);  
   input logic        op;  
   input logic  [7:0] x;  
   input logic  [7:0] y;  
   output logic [7:0]	res;  

   reg [7:0]	res; 

   always @(x or y or op)  
   begin  
      if (op == 1'b0)
	res = x + y;  
      else
        res = x - y;  
   end  
endmodule // AddSub3
*/

// Unsigned 8-bit greater or equal comparator. 
module Geq(x, y, res);  
   input  logic  [7:0]  x;  
   input  logic  [7:0]	y;  
   output logic		res;  

   assign res = x >= y ? 1'b1 : 1'b0;  
endmodule // Geq

// Unsigned 8x8-bit multiplier. 
module Mul(x, y, res);  
   input  logic  [7:0]  x;  
   input  logic  [7:0]  y;  
   output logic  [15:0] res; 
 
   assign res = x * y;  
endmodule // Mul

