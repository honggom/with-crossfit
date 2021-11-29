import styles from './MyName.module.css';

export default function MyName({ name }) {

    return (
        <>
            {name && (<span>이름 : {name}</span>)}
        </>
    );
}