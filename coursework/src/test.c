#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
	char *file_name = "hello.txt";
	char *file_path = realpath(file_name, NULL);


	if (file_path == NULL) {
		printf("Invalid file path: %s\n", file_name);
		exit(-1);
	}

	FILE *file = fopen(file_path, "r");

	unsigned int record_count;
	fscanf(file, "%d\n", &record_count);

	printf("Record count = %d\n", record_count);

	// if (file == NULL) {
	// 	printf("File not found: %s\n", file_name);
	// 	exit(-1);
	// }

	// while (!feof(file)) {
	// 	unsigned int digit;
	// 	char word[1000];

	// 	// fgets(word, 100, file);

	// 	fscanf(file, "%d %s\n", &digit, word);

	// 	printf("%d -> %s\n", digit, word);
	// }

	// for (unsigned int i = 0; i < record_count; i++) {
	// 	unsigned int digit;
	// 	char word[1000];

	// 	// fgets(word, 100, file);

	// 	fscanf(file, "%d %s\n", &digit, word);

	// 	printf("%d: %d -> %s\n", i, digit, word);

	// }

	// OR

	unsigned int i;
	while (!feof(file)) {
		i++;
		unsigned int digit;
		char word[1000];

		// fgets(word, 100, file);

		fscanf(file, "%d %s\n", &digit, word);

		printf("%d: %d -> %s\n", i, digit, word);
	}


	// fclose(file);

	return 0;
}
	