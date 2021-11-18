import TextEditor from '../../component/TextEditor';
import styles from './WriteWod.module.css';
import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import { insertWod } from '../../api/pages/WriteWod';
import { useNavigate } from 'react-router-dom';
import { errorHandle } from '../../util/util';

export default function WriteWod() {

    let navigate = useNavigate();

    const today = new Date();
    const tomorrow = today.setDate(today.getDate() + 1);

    const [date, setDate] = useState(new Date(tomorrow));
    const [title, setTitle] = useState('');
    const [text, setText] = useState('');

    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <p>WOD 작성</p>
            </div>

            <div className={styles.middleWrapper1}>
                <span>WOD 일시</span>
                <DatePicker className={styles.border} selected={date} onChange={(date) => {
                    setDate(date);
                }} />
            </div>

            <div className={styles.middleWrapper1}>
                <span>제목</span>
                <input value={title}
                    type={'text'}
                    className={styles.border}
                    onChange={({ target: { value } }) => {
                        setTitle(value);
                    }}></input>
            </div>

            <div className={styles.middleWrapper2}>
                <span>본문</span>
                <TextEditor setText={setText} />
            </div>

            <div className={styles.bottomWrapper}>
                <button className={styles.button}
                    onClick={() => {
                        console.log(text);
                        console.log(title);
                        console.log(date);
                        if (date === null) {
                            alert('날짜를 선택해주세요.');
                        } else if (title === '') {
                            alert('제목을 작성해주세요.');
                        } else {
                            insertWod(date, title, text).then((response) => {
                                alert('작성 되었습니다.');
                                navigate('/home', {replace: true})
                            }).catch((error) => {
                                if (error.response.status === 400) {
                                    alert(error.response.data);
                                } else {
                                    errorHandle(error, navigate)
                                }
                            });
                        }
                    }}>작성</button>
            </div>
        </div>
    );

}