import TextEditor from '../../component/TextEditor';
import styles from './WriteWod.module.css';
import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";

export default function WriteWod() {

    const today = new Date();
    const tomorrow = today.setDate(today.getDate() + 1);

    const [date, setDate] = useState(new Date(tomorrow));
    const [text, setText] = useState('');

    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <p>WOD 작성</p>
            </div>

            <div className={styles.middleWrapper1}>
                <DatePicker selected={date} onChange={(date) => {
                    setDate(date);
                }} />
            </div>

            <div className={styles.middleWrapper2}>
                <TextEditor setText={setText} />
            </div>

            <div className={styles.bottomWrapper}>
                <button className={styles.button} 
                onClick={() => {
                    console.log(text);
                    console.log(date);
                }}>작성</button>
            </div>

        </div>
    );

}