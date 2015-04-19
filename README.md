# Document-at-a-time (DAAT) query processing with WAND optimization

In particular, for each query, posting lists are fetched(from the disk) that correspond to the query terms from the index. 
Then, as assumed disjunctive semantics (which means that computation of the ranking score for each document
that includes at least one of the query terms), first typical DAAT processing is done
until the first k documents are scored and inserted into a heap. 
From this point on, someone can switch to WAND processing, which means that the score of a
document is computed only if it can exceed the score threshold (i.e., the minimum score in the heap) based
on the term upper-bounds; and otherwise, is skipped. 

Counts of the evaluated and skipped posting elements per query are counted, the average counts are reported over the query set.

# Data files:

* Dictionary file:

For each line

[string token]	[int length-of-postings-list]	[float precomputed-idf]

* Query file:

Each line is a [string query]

* Vector lengths file:	

For each line

[int document-id]	[float document-length]

* Postings binary file:	

Each posting is a form of 

[int document-id, int term-frequency]

and each integer takes 4 bytes in Little Endian form.

# Additional documents

* PA-2015.pdf: Exact problem definition.