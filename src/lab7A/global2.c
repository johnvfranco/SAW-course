#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

uint32_t x = 6;

uint32_t f ( uint32_t y ) {
   x = x + 1;
   return x + y ;                                              
}

uint32_t g ( uint32_t z ) {
   x = x + 2;
   return x + z ;
}
                                     	
uint32_t gf (uint32_t w) {
	return g(f(w));
}

int main (int argc, char **argv) {
	x=6;
	printf("f(1)=%d x=%d\n",f(1),x);
	x=6;
	printf("g(1)=%d x=%d\n",g(1),x);
	x=6;
	printf("gf(6)=%d x=%d\n", gf(6),x);
}
