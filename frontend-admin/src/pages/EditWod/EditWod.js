import { useLocation, useNavigate } from 'react-router-dom';
import TextEditor from '../../component/TextEditor';
import styles from './EditWod.module.css';
import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import { errorHandle } from '../../util/util';
import { updateWod } from '../../api/pages/EditWod';

export default function EditWod() {

    let navigate = useNavigate();

    const { state } = useLocation();

    const [date, setDate] = useState(new Date(state.date));
    const [title, setTitle] = useState(state.title);
    const [text, setText] = useState(state.content);

    return (
        <div className={styles.wrapper}>

            <div className={styles.topWrapper}>
                <p>WOD 수정</p>
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
                <TextEditor text={text} setText={setText} />
            </div>

            <div className={styles.bottomWrapper}>
                <button className={styles.button} onClick={() => {
                    if (date === null) {
                        alert('날짜를 선택해주세요.');
                    } else if (title === '') {
                        alert('제목을 작성해주세요.');
                    } else {
                        updateWod(state.id, date, title, text).then((response) => {
                            alert('수정 되었습니다.');
                            navigate('/wod-history', {replace: true})
                        }).catch((error) => {
                            if (error.response.status === 400) {
                                alert(error.response.data);
                            } else {
                                errorHandle(error, navigate)
                            }
                        });
                    }
                }}>수정</button>
            </div>
        </div>
    );
}
