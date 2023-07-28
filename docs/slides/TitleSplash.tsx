import React from 'react';
import { fadeTransition, Heading, SlideLayout } from 'spectacle';

export const TitleSplash = () => (
    <SlideLayout.Center transition={fadeTransition}>
        <Heading fontWeight="300" fontSize="100px">
            Introduksjon til spillutvikling med Kotlin
        </Heading>
    </SlideLayout.Center>
);
