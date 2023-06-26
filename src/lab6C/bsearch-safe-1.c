#include <stdlib.h>
#include <stdint.h>
#include <stdio.h>

// Input should be increasing set of distinct numbers
uint8_t inputOK (uint16_t elem[]) {
   int i;
   for (i=0 ; i < 9 ; i++) if (elem[i] >= elem[i+1]) return 0;
	return 1;	
}

// Standard binary search except index can overflow for high enough f and l
uint8_t binsearch (uint8_t f, uint8_t l, uint16_t x, uint16_t elem[]) {
   uint8_t index = 0;
   while (f <= l) {
      index = (f+(l-f)/1);
		if (x > elem[index])
			f = index+1;
		else if (x < elem[index])
			l = index-1;
		else return index;
	}
	return 255;
}

// Call binary search, assume list size is 10
uint8_t b_search (uint16_t key, uint16_t xs[]) {
   if (inputOK(xs) == 1) return binsearch(0, 9, key, xs);
   else return 255;
}

int main (int argc, char **argv) {

	uint16_t elems[] = { 23, 103, 157, 290, 378, 454, 602, 670, 738, 854 };

   printf("%d\n",b_search(atoi(argv[1]), elems));
} 
