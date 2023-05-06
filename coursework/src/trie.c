#include <math.h>

struct Trie {
	struct Trie *(children[10]);
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

	trie_node root_trie;


	// for (unsigned char i = 0; i < strlen(code_as_str); i++) {
	// 	printf("%c\n", code_as_str[i]);


	// 	trie.tries[i].test = 1;

	// }

	// for ()
	// trie.test = 1;
	// printf("trie test: %i\n", trie.test);


	// trie_node trie_child = trie.tries[0];

	// trie_node child;
	// printf("trie test: %i\n", trie.test);
	
	// trie_node *ptr = (trie_node *) malloc(sizeof(trie_node));

	trie_node child;

	child.test = 10;

	root_trie.children[0] = &child;

	printf("%i\n", root_trie.children[0] -> test);

	// for (int i = 0; i < 10; i++) {
		// printf("%i\n", root_trie.children[i] -> test);
	// }
}