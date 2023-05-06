#include <math.h>

struct Trie {
	struct Trie **children;
	product_t *product;
	int test;
};
// struct Tile *tries;
typedef struct Trie trie_node;

// void insert_into_trie(trie_node *root_node, product *product) {
void insert_into_trie(unsigned int code) {

	// Create char string from numeric code
	char code_as_str[(int) log10(code) + 1];
	sprintf(code_as_str, "%i", code);

	printf("%s\n", code_as_str);

	trie_node **child_ptrs = malloc(sizeof(trie_node *) * 10); 

	trie_node root_trie = { .children = child_ptrs };
	trie_node *child_node;


	for (unsigned short int i = 0; i < strlen(code_as_str); i++) {
		printf("i = %u\n", i);

		char c = code_as_str[i];

		printf("c = %c\n", c);


		unsigned short int digit = atoi(&c);

		printf("Looking for child: %u\n", digit);


		if (root_trie.children[digit]) {
			printf("Child exists: %u\n", digit);
			child_node = root_trie.children[digit];
		} else {
			printf("Child [%u] doesn't exist\n", digit);
			trie_node child = { .test = 9 };
			root_trie.children[digit] = &child;
		}
		printf("child node test = %u\n", root_trie.children[digit] -> test);
	}

	for (int i = 0; i < 10; i++) {
		if (root_trie.children[i]) {
			printf("%i\n", root_trie.children[i] -> test);
		} else {
			printf("Child [%i] doesn't exist\n", i);
		}
	}
}