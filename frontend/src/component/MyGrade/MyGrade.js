import styles from './MyGrade.module.css';

export default function MyGrade({ grade }) {

    return (
        <>
            {grade && (<span>등급 : {grade}</span>)}
        </>
    );
}