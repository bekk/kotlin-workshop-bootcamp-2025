import React from 'react';
import { SlideLayout, fadeTransition, Heading, Notes, Text } from 'spectacle';

export const Demo = () => (
    <SlideLayout.Center transition={fadeTransition}>
        <Heading fontWeight="300" fontSize="100px">
            Demo
        </Heading>
        <Notes>
            <Text>Vegard viser frem spillet sitt :D</Text>
        </Notes>
    </SlideLayout.Center>
);
