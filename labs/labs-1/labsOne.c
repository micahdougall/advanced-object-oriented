#include <stdio.h>
#include <stdlib.h>

void taskOne(char favouriteLanguage[], char birthMonth[], char holidayDestination[]) {
	printf("    -> Your most loved programming language is %s\n", favouriteLanguage);
	printf("    -> The month of your birthday is %s\n", birthMonth);
	printf("    -> Your favourite holiday destination is %s\n", holidayDestination);
}

void taskTwo(int number) {
	unsigned int num = number;
	unsigned long long total = 1;

	while (number) {
		total *= number;
		number--;
	}
	printf("	-> %u factorial = %llu\n", num, total);
}

void taskFour(unsigned int numCount, int numbers[]) {
	unsigned int total = 0;

	for (; numCount; numCount--) {
		total += numbers[numCount - 1];
	}
	printf("	-> Sum of numbers array = %u\n", total);
}


int main(int argc, char* argv[]) {
	printf("Executing %s...\n", argv[0]);

	int option = atoi(argv[1]);

	switch (option) {
		case 1:
			printf("You selected task 1:\n\n");

			taskOne(argv[2], argv[3], argv[4]);
			break;
		case 2:
			printf("You selected task 2:\n\n");

			taskTwo(atoi(argv[2]));
			break;
		case 4:
			printf("You selected task 4\n\n");

			unsigned int arrSize;
			arrSize = argc - 2;
			int numbers[arrSize];

			for (int i = 2; i < argc; i++) {
				numbers[i - 2] = atoi(argv[i]);
			}
			taskFour(arrSize, numbers);
			break;
		// default:
			// printf("Invalid option\n");
	}
	return 0;
}