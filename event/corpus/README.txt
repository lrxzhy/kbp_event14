   TAC 2014 KBP Event Argument Extraction Pilot Source Corpus V1.1
                              LDC2014E20

                           April 1, 2014
                      Linguistic Data Consortium    

0.1 Update

The README in the preliminary version of this package did not include
the correct catalog ID number. This update corrects that error.

1. Introduction

Text Analysis Conference (TAC) is a series of workshops organized by
the National Institute of Standards and Technology (NIST).  TAC was
developed to encourage research in natural language processing (NLP)
and related applications by providing a large test collection, common
evaluation procedures, and a forum for researchers to share their
results.  Through its various evaluations, the Knowledge Base
Population (KBP) track of TAC encourages the development of systems
that can match entities mentioned in natural texts with those
appearing in a knowledge base and extract novel information about
entities from a document collection and add it to a new or existing
knowledge base.

Event Argument Extraction (EAE), a new task for TAC KBP in 2014, aims to
extract information about entities (and times) and the roles they play
in events. Since the ultimate goal is to add the extracted information 
to a knowledge base, the task requires systems to extract tuples that 
indicate the event type, an argument within the event, and the role 
the argument played in the event. For more information
about Event Argument Extraction, refer to the track home page at
www.nist.gov/tac/2014/KBP/Event/index.html

This package contains 60 source documents for utilization in a pilot run 
of the Event Argument Extraction task. Documents were manually selected 
by annotators based on their containment of at least one event type from 
the EAE event taxonomy.


2. Contents

./README.txt

  This file.

./data/df/*

  This data directory holds 30 XML files, all of which are discussion
  forum threads originating from the TAC KBP 2013 Source Corpus 
  (LDC2013E45).  

./data/nw/*

  This data directory holds 30 XML files, all of which are English 
  newswire documents originating from the TAC KBP 2013 Source Corpus 
  (LDC2013E45). 

3. Newswire Data Markup Framework

Newswire data use the following markup framework:

  <DOC id="{doc_id_string}" type="{doc_type_label}">
  <HEADLINE>
  ...
  </HEADLINE>
  <DATELINE>
  ...
  </DATELINE>
  <TEXT>
  <P>
  ...
  </P>
  ...
  </TEXT>
  </DOC>

where the HEADLINE and DATELINE tags are optional (not always
present), and the TEXT content may or may not include "<P> ... </P>"
tags (depending on whether or not the "doc_type_label" is "story").

If a suitable "container" or "root" tag is applied at the beginning
and end of each *.gz stream, all the newswire files are parseable as
XML.

4. Discussion Forum Data Markup Framework

Discussion forum files use the following markup framework:

  <doc id="{doc_id_string}">
  <headline>
  ...
  </headline>
  <post ...>
  ...
  <quote ...>
  ...
  </quote>
  ...
  </post>
  ...
  </doc>

where there may be arbitrarily deep nesting of quote elements, and
other elements may be present (e.g. "<a...>...</a>" anchor tags).
Additionally, each <doc> unit contains at least five post elements.

If a suitable "container" or "root" tag is applied at the beginning
and end of each *.gz stream, all the discussion forum files are
parseable as XML.
  

5. Copyright Information

(c) 2014 Trustees of the University of Pennsylvania


6. Contact Information

For further information about this data release, or the TAC 2014 KBP
project, contact the following project staff at LDC:

    Joe Ellis, Project Manager           <joellis@ldc.upenn.edu>
    Jeremy Getman, Lead Annotator        <jgetman@ldc.upenn.edu>
    Steve Grimes, Lead Corpus Developer  <sgrimes@ldc.upenn.edu>
    Stephanie Strassel, Consultant       <strassel@ldc.upenn.edu> 

--------------------------------------------------------------------------
README created by Joe Ellis on March 21, 2014
       updated by Joe Ellis on March 27, 2014
