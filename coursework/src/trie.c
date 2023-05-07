#include <math.h>

struct Trie {
	struct Trie **children;
	product_t *product;
	int test;
};
typedef struct Trie trie_node;



void print_node(trie_node *node, char *route) {
	if (route == NULL) {
		printf("\nStarting at root node...\n\n");
	} else {
		printf("Printing for route %s...\n", route);
	}

	printf("%s -> %d\n", route, node -> test);

	// TODO: Use size_t for safety
	for (int i = 0; i < 10; i++) {
		char int_str[2];

		if (node -> children[i]) {
			sprintf(int_str, "%d", i);

			if (route == NULL) {
				route = int_str;
			} else {
				strcat(route, int_str);
			}
			printf("Child found at route: %s\n", route);
			print_node(node -> children[i], route);
		}
	}
}


// void insert_into_trie(trie_node *root_node, product *product) {
void insert_into_trie(unsigned int code) {

	// Create char string from numeric code
	char code_as_str[(int) log10(code) + 1];
	sprintf(code_as_str, "%i", code);

	printf("Code as string: %s\n", code_as_str);

	// Create root node
	trie_node root_node = { 
		.children = malloc(sizeof(trie_node *) * 10)
	};
	printf(" -> root_node is %p\n", &root_node);

	trie_node *current_node_ptr = &root_node;
	printf(" -> current_node_ptr is %p\n", current_node_ptr);

	trie_node *new_node = (trie_node *) malloc(sizeof(trie_node *));

	for (unsigned short int c = 0; c < strlen(code_as_str); c++) {
		char code_char = code_as_str[c];
		unsigned short int idx = atoi(&code_char);

		printf("\n\nLooking for child: %u\n", idx);

		// Assign pointer to the relevant index
		// trie_node *child_node = current_node_ptr -> children[idx];

		if (current_node_ptr -> children[idx]) {
			printf("Child exists: %u\n", idx);
		} else {
			printf("Child [%u] doesn't exist\n", idx);
			printf(" -> child_node is %p\n", current_node_ptr -> children[idx]);



			// Create new child node
			// new_node.

			trie_node **children = malloc(sizeof(trie_node *) * 10);

			trie_node *new_node = malloc(sizeof(trie_node *));
			new_node -> children = children;

			current_node_ptr -> children[idx] = new_node;
			// current_node_ptr -> children[idx] = *new_node;
			printf(" -> child_node is %p\n", current_node_ptr -> children[idx]);


			// *(new_node -> children) = malloc(sizeof(trie_node *) * 10);

			// trie_node new_node = { 
				// .children = malloc(sizeof(trie_node *) * 10) 
			// };
			printf(" -> new_node is %p\n", new_node);
			printf(" -> new_node is %p\n", &new_node);

			// char test[10];
			// printf(" -> test is %p\n", &test);

			// child_node = &new_node;
			*(current_node_ptr -> children[idx]) = *new_node;
			printf("Added new child at branch %u\n", idx);
			printf(" -> child_node is %p\n", current_node_ptr -> children[idx]);
		}

		current_node_ptr = current_node_ptr -> children[idx];
		printf(" -> current_node_ptr is %p\n", current_node_ptr);

	}
	// For proof of tree work where product would be
	current_node_ptr -> test = 10;

	printf(" -> root_node is %p\n", &root_node);
	print_node(&root_node, NULL);
}
