#include <math.h>

/**
 * Trie - Stores a product along with its calculated score.
 * @children: Array or pointers to .
 * @score: Its score.
 * 
 * Simplified with typedef product_ext.
 */
typedef struct Trie {
	struct Trie** children;
	product_t* product;
	int test;
} trie_node;


/**
 * print_trie() - Prints a Trie structure.
 * @node: A pointer to the node in the trie being printed.
 * @indent: Amount to indent the child (depth level).
 * @child_idx: The index of the child node being printed
 * 
 * Works iteratively through the trie and prints each node using print_node(),
 * to which the route of the node is passed.
 */
void print_trie(
	trie_node* node, unsigned int indent, unsigned int child_idx
) {

	char* product_name = (node -> product)
		? (node -> product) -> name
		: "";

	char* spacing = (char*) malloc(sizeof(char) * indent);
	// spacing[0] = '-';
	for (unsigned int i = 0; i < indent; i++) {
		spacing[i] = '-';
	}

	printf("%s %u %s\n", spacing, child_idx, product_name);

	for (unsigned int i = 0; i < 10; i++) {
		if (node -> children[i]) {
			print_trie(node -> children[i], indent + 1, i);
		}
	}
}


/**
 * lookup_product() - Searches for a specified product in a Trie structure.
 * @node: The root node of the trie to search.
 * @product_code: The unique code of the product to search.
 * 
 * The algorithm is recursive such that the root node provided as an argument
 * need not be the root node of the entire trie structure, but a subset of it.
 * 
 * The product_code indicates the unique address where the product should be 
 * stored within the trie.
 * 
 * Returns: A pointer to the product required, or NULL if no product with the
 * supplied code is found within the trie.
 */
product_t* lookup_product(trie_node* node, unsigned int product_code) {

	if (node -> product && (node -> product) -> code == product_code) {
		return node -> product;
	} else {
		for (unsigned int i = 0; i < 10; i++) {
			if (node -> children[i]) {
				product_t *product = lookup_product(node -> children[i], product_code);

				if (product) {
					return product;
				}
			}
		}
		return NULL;
	}
}


/**
 * insert_into_trie() - Inserts an array or products into a Trie structure.
 * @*root_node: A pointer to the root node of the trie.
 * @*product: An array of products to insert into the trie.
 * 
 * The algorithm uses the unique product.code to insert each product into the
 * trie where each digit in the code denotes the index for a child node from
 * the predceding parent node.
 */
void insert_into_trie(trie_node* root_node, product_t* product) {

	// Create string from numeric code (accounting for number size)
	char code_as_str[(int) log10(product -> code) + 1];
	sprintf(code_as_str, "%i", product -> code);

	trie_node* current_node_ptr = root_node;

	for (unsigned short int c = 0; c < strlen(code_as_str); c++) {
		char code_char = code_as_str[c];
		unsigned short int idx = atoi(&code_char);

		if (!current_node_ptr -> children[idx]) {

			// Create new child node
			trie_node* new_node = (trie_node*) malloc(sizeof(trie_node*));
			trie_node** children = (trie_node**) malloc(sizeof(trie_node**) * 10);

			// Update pointers
			new_node -> children = children;
			current_node_ptr -> children[idx] = new_node;

			*(current_node_ptr -> children[idx]) = *new_node;
		}
		current_node_ptr = current_node_ptr -> children[idx];
	}
	current_node_ptr -> product = product;
}
