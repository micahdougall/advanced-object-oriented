int factorial_recursive(unsigned int num) {
	if (num > 20) {
		printf("\nNumber too large to compute!\n");
		exit(-1);
	} else if (num == 1) {
		return num;
	} else {
		return num * factorial_recursive(num - 1);
	}
}