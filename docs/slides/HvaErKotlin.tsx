import React from 'react';
import {SlideLayout, Text, FlexBox, Image, fadeTransition, Heading, UnorderedList, ListItem, Notes} from 'spectacle';


export const HvaErKotlin = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading fontWeight="300" fontSize="100px">
            Hva er Kotlin?
        </Heading>
        <UnorderedList>
            <ListItem>Programmeringspråk</ListItem>
            <ListItem>JVM, veldig god interop med Java</ListItem>
            <ListItem>Smooth</ListItem>
        </UnorderedList>
        <Notes>
            <Text>Programmerspråk som er statisk typa, ligner en del på Java. Mer funksjonelt, veldig my syntakssukker.</Text>
            <Text>...</Text>
        </Notes>
    </SlideLayout.Full>
);
