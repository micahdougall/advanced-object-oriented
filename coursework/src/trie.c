#include <math.h>

/**
 * Trie - Stores a product along with its calculated score.
 * @**children: Array or pointers to .
 * @score: Its score.
 * 
 * Simplified with typedef product_ext.
 */
typedef struct Trie {
	struct Trie **children;
	product_t *product;
	int test;
} trie_node;


/**
 * print_node() - Prints a single node within a trie.
 * @node: A pointer to the node to print.
 * @route: The address of the node within the trie.
 * 
 * The node is not aware of its own parent structure so the route provides a 
 * way to include the full address of the node as part of the output.
 */
void print_node(trie_node *node, char *route) {

	if (node -> product) {
		printf("%s -> %s\n", route, (node -> product) -> name);
	}


	for (int i = 0; i < 10; i++) {
		char *int_str = (char *) malloc(sizeof(char));
		sprintf(int_str, "%d", i);

		strcpy(route, int_str);
		// strcat(route, int_str);
		printf("%s\n", route);

		// if (node -> children[i]) {
		// 	sprintf(int_str, "%d", i);

		// 	// if (route == NULL) {
		// 		// route = int_str;
		// 	// } else {
		// 	strcat(route, int_str);
		// 	// }

		// 	print_node(node -> children[i], route);
		// }
	}
	// free(int_str);
}

/**
 * print_trie() - Prints a Trie structure in its entirety.
 * @root_node: A pointer to the root node of the trie.
 * 
 * Works iteratively through the trie and prints each node using print_node(),
 * to which the route of the node is passed.
 */
void print_trie(trie_node *root_node) {

	if (root_node -> product) {
		printf(" -> %s\n", (root_node -> product) -> name);
	}

	for (unsigned int i = 0; i < 10; i++) {
		if (root_node -> children[i]) {
			print_trie(root_node -> children[i]);
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
 * 
 * Returns: void
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
