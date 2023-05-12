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
	trie_node* node, unsigned int child_idx, unsigned int depth, char* edges
) {
	// Product name string if exists
	// char* product_name = (node -> product)
		// ? (node -> product) -> name
		// : "";
	// unsigned int product_code = (node -> product)
		// ? *(node -> product) -> code
		// ? 1
		// : NULL;
	// unsigned int product_code = 1
	// char* int product_code = (node -> product) ? (node -> product) -> code : NULL;
	char* product_code = (char*) malloc(sizeof(char) * 12);

	if (node -> product) {
		sprintf(product_code, " -> %d", (node -> product) -> code);
	} else {
		product_code = "";
	}

	// printf("%s%u %s\n", edges, child_idx, product_name);
	printf("%s%u %s\n", edges, child_idx, product_code);

	// Array of edges for child nodes as a count of children for each position
	char* children_edges = (char*) malloc(sizeof(char) * depth * 2 + 2);
	for (unsigned int i = 0; i <= depth * 2; i++) {
		children_edges[i] = (edges[i] == '\\') ? ' ' : edges[i];
	}

	unsigned int last;
	for (unsigned int j = 0; j < 10; j++) {
		if (node -> children[j]) {
			last = j;
		}
	}

	// Print each child
	for (unsigned int i = 0; i < 10; i++) {
		if (node -> children[i]) {

			// Get last child position
			// unsigned int last;
			// for (unsigned int j = 0; j < 10; j++) {
			// 	if ((node -> children[i]) -> children[j]) {
			// 		last = j;
			// 	}
			// }
			// printf("For child %u, last is %u\n", i, last);


			char* child_edges = (char*) malloc(strlen(children_edges) + 2);
			strcpy(child_edges, children_edges);

			// char* append = (char*) malloc(sizeof(char) * 2);
			// append = (i == last) 
				// ? " \\" 
				// : "|\\";



			if (i == last) {
				strcat(child_edges, "\\" );
			} else {
				strcat(child_edges, "|\\" );
			}
			// strcat(child_edges, append);
			// free(append);

			if (child_idx <= 100) {
				print_trie(node -> children[i], i, depth + 1, child_edges);
			}
		}
	}
}

// For child 1, last is 7
// For child 2, last is 8
// For child 6, last is 4
// For child 3, last is 8
// For child 8, last is 1
// For child 1, last is 6
// For child 6, last is 0
// For child 0, last is 9
// For child 9, last is 1277329409
// For child 4, last is 1
// For child 1, last is 0
// For child 0, last is 7
// For child 7, last is 2
// For child 2, last is 0
// For child 0, last is 7
// For child 7, last is 1662615553

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
