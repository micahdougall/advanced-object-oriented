void find_indices(unsigned int x, unsigned int num_count, int numbers[]) {
	printf("Evaluating array [");
	for (unsigned int i = 0; i < num_count; i++) {
		if (i == 0) {
			printf("%d", numbers[i]);
		} else {
			printf(", %d", numbers[i]);
		}
	}
	printf("] for a total of %d...\n", x);

	for (unsigned int a = 0; a < num_count - 2; a++) {
		unsigned int val_a = numbers[a];

		for (unsigned int b = a + 1; b < num_count - 1; b++) {
			unsigned int val_b = numbers[b];

			for (unsigned int c = b + 1; c < num_count; c++) {
				unsigned int val_c = numbers[c];

				if (val_a + val_b + val_c == x) {
					printf("\nFound a combination!\n");
					printf("	-> Indices %d, %d and %d (values: %d, %d, %d)"
						" add up to %d\n", 
						a, b, c, val_a, val_b, val_c, x);
					return;
				}
			}
		}
	}
	printf("\nNo combination found.\n");
	return;
}