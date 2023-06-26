#include <stdlib.h>
#include <stdint.h>
#include <stdio.h>

// Input should be increasing set of distinct numbers
uint8_t inputOK (uint16_t elem[]) {
   int i;
   for (i=0 ; i < 232 ; i++) if (elem[i] >= elem[i+1]) return 0;
	return 1;	
}

// Standard binary search except index can overflow for high enough f and l
uint8_t binsearch (uint8_t f, uint8_t l, uint16_t x, uint16_t elem[]) {
   uint8_t index = 0;
   while (f <= l) {
      index = (f+l);
		index = index/2;
      if (x == elem[index]) return index;
      if (x < elem[index]) l = index-1;
      else f = index+1;
	}
	return 255;
}

// Call binary search, assume list size is 233
uint8_t b_search (uint16_t key, uint16_t xs[]) {
   if (inputOK(xs) == 1) return binsearch(0, 232, key, xs);
   else return 255;
}

int main (int argc, char **argv) {

	uint16_t elems[] = {
         23,  26,  33,  40,  49,  50,  53,  60,  62,  67,  71,  77,  80,  85,  94,
        103, 109, 116, 117, 123, 124, 125, 126, 134, 135, 139, 140, 141, 150, 152,
        157, 165, 171, 173, 179, 184, 188, 197, 199, 206, 211, 218, 222, 230, 232,
        235, 243, 252, 253, 261, 265, 266, 267, 273, 281, 284, 285, 286, 288, 289,
        290, 299, 308, 316, 318, 323, 326, 333, 338, 344, 349, 350, 353, 362, 371,
        378, 380, 387, 393, 396, 402, 403, 407, 415, 423, 426, 428, 436, 439, 445,
        454, 459, 465, 473, 477, 486, 489, 497, 503, 512, 516, 518, 527, 536, 537,
        545, 551, 555, 561, 563, 570, 573, 577, 579, 580, 582, 587, 591, 592, 601,
        602, 603, 607, 614, 623, 632, 638, 641, 648, 651, 653, 655, 660, 662, 663,
        670, 671, 678, 679, 686, 687, 695, 696, 700, 701, 703, 710, 717, 723, 732,
        738, 744, 745, 746, 750, 751, 752, 753, 757, 765, 770, 776, 777, 778, 787,
		  789, 797, 798, 807, 808, 815, 816, 817, 824, 829, 830, 831, 834, 843, 851,
        854, 859, 864, 868, 874, 875, 879, 886, 889, 898, 903, 911, 918, 924, 925,
        931, 939, 948, 956, 964, 973, 980, 989, 998, 1004, 1008, 1009, 1017, 1025,
       1031, 1033, 1039, 1041, 1048, 1051, 1054, 1056, 1057, 1062, 1064, 1070,
       1075, 1084, 1086, 1091, 1098, 1099, 1103, 1108, 1117, 1121, 1124, 1130 };

   printf("%d\n",b_search(atoi(argv[1]), elems));
} 
