import Modal from 'react-modal';
import styles from './WriteSpecificScheduleModal.module.css';
import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { errorHandle } from '../../util/util';
import TextField from '@mui/material/TextField';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import TimePicker from '@mui/lab/TimePicker';
import EachTime from '../EachTime/EachTime';
import { insertSpecificSchedule, insertDayOffSpecificSchedule } from '../../api/component/WriteSpecificScheduleModal/WriteSpecificScheduleModal';

export default function WriteSpecificScheduleModal({ isOpen, setIsOpen, dateStr }) {

    let navigate = useNavigate();

    const overlay = {
        zIndex: '1'
    }

    const content = {
        display: 'flex',
        flexDirection: 'column',
        position: 'absolute',
        top: '15%',
        height: '60%',
        margin: 'var(--comm-margin)',
        borderRadius: 'var(--comm-border-radius)',
        overflowX: 'hidden',
    }

    const [name, setName] = useState('');
    const [start, setStart] = useState(null);
    const [end, setEnd] = useState(null);
    const [eachTimes, setEachTimes] = useState([]);

    return (
        <>
            <Modal isOpen={isOpen} style={{ overlay: overlay, content: content }}>
                <div className={styles.titleWrapper}>
                    <span>별도 시간표 작성 ({dateStr})</span>
                </div>

                <div className={styles.rowWrapper1}>
                    <span className={styles.fontStyle}>시간표 명</span>
                    <input className={styles.inputStyle}
                        value={name}
                        type={'text'}
                        onChange={(e) => {
                            setName(e.target.value);
                        }}
                    >
                    </input>
                </div>

                <div className={styles.rowWrapper2}>

                    <div className={styles.eachTimeWrapper}>
                        <EachTime eachTimes={eachTimes} setEachTimes={setEachTimes} />
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

                        <button className={styles.addButtonStyle}
                            onClick={() => {
                                if (start === null || end === null) {
                                    alert('시간을 선택해주세요.');
                                } else {
                                    setEachTimes([...eachTimes, { start: start.toTimeString().substring(0, 5), end: end.toTimeString().substring(0, 5) }]);
                                }
                            }}
                        >
                            추가
                        </button>
                    </div>
                </div>

                <div className={styles.rowWrapper3}>
                    <button className={styles.setToHolidayButton}
                            onClick={() => {
                                if(window.confirm('휴무로 설정하시겠습니까?')){
                                    insertDayOffSpecificSchedule(dateStr).then((response) => {
                                        alert('휴무로 설정되었습니다.');
                                        window.location.reload();
                                    }).catch((error) => {
    
                                    });
                                }
                            }}        
                    >
                        휴무로 설정
                    </button>
                    <button className={styles.submitButtonStyle}
                            onClick={() => {
                                if (name === '') {
                                    alert('시간표 명을 작성해주세요.');
                                } else if (eachTimes.length === 0) {
                                    alert('시간표를 최소 1개 이상 작성해주세요.');
                                } else {
                                    insertSpecificSchedule(name, eachTimes, dateStr).then((response) => {
                                        alert('작성되었습니다.');
                                        window.location.reload();
                                    }).catch((error) => {
                                        errorHandle(error, navigate);
                                    })
                                }
                            }}
                    >
                        작성
                    </button>
                    <button className={styles.closeButtonStyle}
                        onClick={() => {
                            setIsOpen(false);
                        }}
                    >
                        X
                    </button>
                </div>
            </Modal>
        </>
    );
}