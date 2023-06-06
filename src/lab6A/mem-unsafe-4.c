#include <stdio.h>

int main() {
	int i;
	int x = 6;
   char y[] = "0123456789";
   char* z = x + y;
	printf(" z:%p  y:%p\n", z, y);
	printf("&z:%p &y:%p &x:%p\n", &z, &y, &x);
	printf("z[0..9]: "); for (i=0 ; i < 10 ; i++) printf("[%c]",(char)z[i]);
	printf("\ny[0..9]: "); for (i=0 ; i < 10 ; i++) printf("[%c]",(char)y[i]);
	printf("\n");
}
