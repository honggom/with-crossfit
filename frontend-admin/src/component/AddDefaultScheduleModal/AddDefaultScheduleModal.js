import Modal from 'react-modal';
import styles from './AddDefaultScheduleModal.module.css';
import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import TimePicker from '@mui/lab/TimePicker';


// TODO
// 1. 시작시간, 종료시간 이벤트

export default function AddDefaultScheduleModal({ modalIsOpen, setModalIsOpen }) {

    const content = {
        display: 'flex',
        flexDirection: 'column',
        position: 'absolute',
        top: '15%',
        height: '60%',
        margin: 'var(--comm-margin)',
        borderRadius: 'var(--comm-border-radius)',
        overflowX: 'hidden'
    }

    const [start, setStart] = useState(null);
    const [end, setEnd] = useState(null);

    return (
        <>
            <Modal isOpen={modalIsOpen} style={{ content: content }}>

                <div className={styles.rowWrapper1}>
                    <span className={styles.fontStyle}>시간표 명</span>
                    <input className={styles.inputStyle}></input>
                </div>

                <div className={styles.rowWrapper2}>

                    <div className={styles.eachTimeWrapper}>
                        <div className={styles.eachTime}>
                            <span>10:00 ~ 11:00</span>
                            <button className={styles.deleteButtonStyle}>X</button>
                        </div>

                        <div className={styles.eachTime}>
                            <span>11:00 ~ 12:00</span>
                            <button className={styles.deleteButtonStyle}>X</button>
                        </div>

                        <div className={styles.eachTime}>
                            <span>10:00 ~ 11:00</span>
                            <button className={styles.deleteButtonStyle}>X</button>
                        </div>

                        <div className={styles.eachTime}>
                            <span>11:00 ~ 12:00</span>
                            <button className={styles.deleteButtonStyle}>X</button>
                        </div>
                    </div>

                    <div className={styles.addWrapper}>
                        <div className={styles.inputWrapper}>

                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <TimePicker
                                    label="시작 시간"
                                    value={start}
                                    onChange={(value) => {
                                        setStart(value);
                                    }}
                                    renderInput={(params) => <TextField {...params} />}
                                />
                            </LocalizationProvider>

                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <TimePicker
                                    label="종료 시간"
                                    value={end}
                                    onChange={(value) => {
                                        setEnd(value);
                                    }}
                                    renderInput={(params) => <TextField {...params} />}
                                />
                            </LocalizationProvider>

                        </div>

                        <button className={styles.addButtonStyle}>추가</button>
                    </div>
                </div>

                <div className={styles.rowWrapper3}>
                    <button className={styles.submitButtonStyle}>작성</button>
                    <button className={styles.closeButtonStyle}
                        onClick={() => {
                            setModalIsOpen(false);
                        }}
                    >
                        X
                    </button>
                </div>

            </Modal>
        </>
    );
}