#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>

// Input should be increasing set of numbers (note: all distinct)
uint8_t inputOK (uint16_t elem[]) {
	int i;
	for (i=0 ; i < 232 ; i++) {
		if (elem[i] >= elem[i+1]) return 0;
	}
	return 1;	
}

uint8_t lin_search (uint16_t key, uint16_t xs[]) {
	uint8_t i;
	if (inputOK(xs) == 0) return -1;
	for (i=0 ; i < 233 ; i++) if (key == xs[i]) return i;
	return -1;
}

int main (int argc, char **argv) {

	uint16_t elems[] =
		{  23,  26,  33,  40,  49,  50,  53,  60,  62,  67,
			71,  77,  80,  85,  94,	103, 109, 116, 117, 123,
	     123, 125, 126, 134, 135, 139, 140, 141, 150, 152,
		  157, 165, 171, 173, 179, 184, 188, 197, 199, 206,
		  211, 218, 222, 230, 232, 235, 243, 252, 252, 261,
		  266, 266, 267, 273, 281, 284, 284, 284, 288, 288,
		  290, 299, 308, 316, 318, 323, 326, 333, 338, 344,
		  349, 349, 353, 362, 371, 378, 380, 387, 393, 396,
		  402, 403, 407, 415, 423, 426, 428, 436, 439, 445,
		  454, 459, 465, 473, 477, 486, 489, 497, 503, 512,
		  516, 518, 527, 536, 537, 545, 551, 555, 561, 563,
		  570, 573, 577, 579, 580, 582, 587, 591, 592, 601,
		  602, 603, 607, 614, 623, 632, 638, 641, 648, 651,
		  653, 655, 660, 662, 663, 670, 671, 678, 679, 686,
		  686, 695, 695, 700, 701, 703, 710, 717, 723, 732,
		  738, 745, 745, 746, 751, 751, 751, 753, 757, 765,
		  770, 777, 777, 778, 787, 789, 797, 798, 807, 807,
		  815, 815, 815, 824, 829, 830, 831, 834, 843, 851,
		  854, 859, 864, 868, 874, 874, 879, 886, 889, 898,
		  903, 911, 918, 924, 924, 931, 939, 948, 956, 964,
		  973, 980, 989, 998, 1004, 1008, 1009, 1017, 1025,
		  1031, 1033, 1039, 1041, 1048, 1051, 1054, 1056, 1056, 1062,
		  1064, 1070, 1075, 1084, 1086, 1091, 1098, 1099, 1103, 1108,
		  1117, 1121, 1124 };

	printf("%d\n",lin_search(atoi(argv[1]), elems));
}