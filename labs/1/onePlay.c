// #include <stdio.h>
// #include <string.h>

// int main(int argc, char *argv[])
// {
// 	char one[6] = "one";
// 	char * two = "two";
// 	char * three = "three";

// 	printf("Comparing strings: %d\n", strncmp(one, two, 2));

// 	strncat(one, two, 2);
// 	printf("Concatenating strings: %s\n", one);

// 	return 0;
// }

// #include <stdio.h>

// int main() {
//     int array[] = {1, 7, 4, 5, 9, 3, 5, 11, 6, 3, 4};
//     int i = 0;

//     while (i < 10) {
//         /* your code goe s here */
//         if (array[i] < 5) {
//         	i++;
//         	continue;
//         }
//         if (array[i] > 10) {
//         	break;
//         }

//         printf("%d\n", array[i]);
//         i++;
//     }

//     return 0;
// }

#include <stdio.h>

int main() {
  int n = 10;

  /* your code goes here */

  /* testing code */
  if (pointer_to_n != &n) return 1;
  if (*pointer_to_n != 11) return 1;

  printf("Done!\n");
  return 0;
}
