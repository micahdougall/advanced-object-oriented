#include <math.h>

struct Trie {
	struct Trie **children;
	product_t *product;
	int test;
};
typedef struct Trie trie_node;



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


// void print_trie(trie_node *node) {

// 	if (node -> product) {
// 		printf(" -> %s\n", (node -> product) -> name);
// 	}

// 	for (unsigned int i = 0; i < 10; i++) {
// 		if (node -> children[i]) {
// 			print_trie(node -> children[i]);
// 		}
// 	}
// }

product_t * lookup_product(trie_node *node, unsigned int product_code) {

	if (node -> product && (node -> product) -> code == product_code) {
		// printf(" -> %s\n", (node -> product) -> name);
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

	// for (unsigned int i = 0; i < 10; i++) {
	// 	if (node -> children[i]) {
	// 		print_trie(node -> children[i]);
	// 	}
	// }
}


void insert_into_trie(trie_node *root_node, product_t *product) {

	// Create char string from numeric code
	char code_as_str[(int) log10(product -> code) + 1];
	sprintf(code_as_str, "%i", product -> code);

	// printf("Code as string: %s\n", code_as_str);

	// printf(" -> root_node is %p\n", root_node);

	trie_node *current_node_ptr = root_node;
	// printf(" -> current_node_ptr is %p\n", current_node_ptr);

	trie_node *new_node = (trie_node *) malloc(sizeof(trie_node *));

	for (unsigned short int c = 0; c < strlen(code_as_str); c++) {
		char code_char = code_as_str[c];
		unsigned short int idx = atoi(&code_char);

		// printf("\n\nLooking for child: %u\n", idx);

		if (current_node_ptr -> children[idx]) {
			continue;
			// printf("Child exists: %u\n", idx);
		} else {
			// printf("Child [%u] doesn't exist\n", idx);
			// printf(" -> child_node is %p\n", current_node_ptr -> children[idx]);

			// Create new child node
			trie_node **children = malloc(sizeof(trie_node *) * 10);

			trie_node *new_node = malloc(sizeof(trie_node *));
			new_node -> children = children;

			current_node_ptr -> children[idx] = new_node;
			// printf(" -> child_node is %p\n", current_node_ptr -> children[idx]);


			// printf(" -> new_node is %p\n", new_node);
			// printf(" -> new_node is %p\n", &new_node);

			*(current_node_ptr -> children[idx]) = *new_node;
			// printf("Added new child at branch %u\n", idx);
			// printf(" -> child_node is %p\n", current_node_ptr -> children[idx]);
		}

		current_node_ptr = current_node_ptr -> children[idx];
		// printf(" -> current_node_ptr is %p\n", current_node_ptr);

	}
	// For proof of tree work where product would be
	current_node_ptr -> test = 10;
	current_node_ptr -> product = product;

	// printf(" -> root_node is %p\n", root_node);
	// print_node(root_node, NULL);
}
