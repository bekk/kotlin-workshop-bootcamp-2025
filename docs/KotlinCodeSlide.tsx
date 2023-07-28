import React from 'react';
import { fadeTransition, SlideLayout, CodePane, Heading } from 'spectacle';
import styled from '@emotion/styled';

type KotlinCodeSlideProps = {
    title: string;
    code: string;
};

export const KotlinCodeSlide = ({ title, code }: KotlinCodeSlideProps) => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading fontWeight="300" fontSize="100px">
            {title}
        </Heading>
        <CodePane language="kotlin">{code}</CodePane>
    </SlideLayout.Full>
);

type KotlinCodeSlideTwoColumnsProps = {
    title: string;
    codeLeft: string;
    codeRight: string;
};

export const KotlinCodeSlideTwoColumns = ({
    title,
    codeLeft,
    codeRight,
}: KotlinCodeSlideTwoColumnsProps) => (
    <MultiColumnnCodeSlide transition={fadeTransition}>
        <Heading fontWeight="300" fontSize="100px">
            {title}
        </Heading>
        <CodeContainer>
            <CodePane language="kotlin">{codeLeft}</CodePane>
            <CodePane language="kotlin">{codeRight}</CodePane>
        </CodeContainer>
    </MultiColumnnCodeSlide>
);

const MultiColumnnCodeSlide = styled(SlideLayout.Full)`
    width: 100%;
    > div {
        width: 90%;
    }
`;

const CodeContainer = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    overflow-y: scroll;
    align-self: center;
    width: 90%;

    div {
        width: 48%;
    }
`;
