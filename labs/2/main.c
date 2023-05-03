#include <stdio.h>
#include <stdlib.h>
#include "main.h"


int main(int argc, char* argv[]) {
	printf("Executing %s...\n", argv[0]);

	int option = atoi(argv[1]);

	switch (option) {
		case 1:
			printf("You selected task 1 (factorials):\n\n");

			int task_one_result = factorial_recursive(atoi(argv[2]));
			printf("\nPrinting %s factorial...", argv[2]);
			printf("%d\n", task_one_result);
			break;
		case 2:
			printf("You selected task 2 (films):\n\n");

			read_file("films.txt");
			break;
	}
	return 0;
}
