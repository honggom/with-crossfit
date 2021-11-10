import React, { useState, useEffect } from 'react';
import styles from './MyRm.module.css';
import { getMyRm, insertMyRm } from '../../api/MyRm';
import Modal from 'react-modal';
import MyRmDto from '../../dto/MyRmDto'
import { useNavigate } from "react-router-dom";
import {
    wrapperStyle, weightWrapperStyle, fontStyle, inputStyle, weightInputStyle, buttonWrapperStyle,
    addButtonWrapperStyle, closeButtonWrapperStyle, addButtonStyle, closeButtonStyle
} from './modalStyle'
import { ifExpired, isRightNumber, calculateToLb, calculateToKg } from '../../util/util';
import Rms from './Rms/Rms';


export default function MyRm() {

    // TODO 추가 모달 레이아웃 수장

    let navigate = useNavigate();
    const [modalIsOpen, setModalIsOpen] = useState(false);

    const [name, setName] = useState("");
    const [repetition, setRepetition] = useState(0);
    const [lb, setLb] = useState(0);
    const [kg, setKg] = useState(0);

    const [rms, setRms] = useState([]);

    useEffect(() => {
        getMyRm().then((response) => {
            ifExpired(response, navigate);
            setRms(response.data);
        }).catch((error) => {
            // TODO 예외처리
        });
    }, []);

    return (
        <div className={styles.myRm}>
            <table>
                <thead>
                    <tr>
                        <th colSpan={5} className={styles.myRmTitle}>내 RM</th>
                    </tr>
                    <tr className={styles.head}>
                        <th className={styles.th1}>운동</th>
                        <th className={styles.th2}>횟수</th>
                        <th className={styles.th3}>중량(lb/kg)</th>
                        <th className={styles.th4}>수정</th>
                        <th className={styles.th5}>%</th>
                    </tr>
                </thead>
                <tbody>
                    <Rms rms={rms} setRms={setRms} />
                    <tr className={styles.plusButtonWrapper} onClick={() => setModalIsOpen(true)}>
                        <td colSpan={5}>
                            <button className={styles.plusButton}>
                                추가
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        
            <Modal isOpen={modalIsOpen} style={{
                overlay: {
                },
                content: {
                    display: 'flex',
                    flexDirection: 'column',
                    position: 'absolute',
                    top: '15%',
                    height: '60%',
                    margin: 'var(--comm-margin)',
                    borderRadius: 'var(--comm-border-radius)',
                },
                nameWrapper: {
                    backgroundColor: 'red'
                }
            }}>
                <div style={wrapperStyle}>
                    <div>
                        <span style={fontStyle}>운동</span>
                    </div>
                    <div>
                        <input
                            style={inputStyle}
                            type="text"
                            value={name}
                            onChange={({ target: { value } }) => {
                                setName(value);
                            }}
                        ></input>
                    </div>
                </div>

                <div style={wrapperStyle}>
                    <div>
                        <span style={fontStyle}>횟수</span>
                    </div>
                    <div>
                        <input
                            style={inputStyle}
                            type="text"
                            value={repetition}
                            onChange={({ target: { value } }) => {
                                if (isRightNumber(value)) {
                                    setRepetition(Number(value));
                                } else {
                                    setRepetition(0);
                                    alert("숫자만 입력해주세요.");
                                }
                            }}
                        ></input>
                    </div>
                </div>

                <div style={wrapperStyle}>
                    <div>
                        <span style={fontStyle}>중량</span>
                    </div>
                    <div style={weightWrapperStyle}>
                        <div>
                            <input
                                style={weightInputStyle}
                                type="text"
                                value={lb}
                                onChange={({ target: { value } }) => {
                                    if (isRightNumber(value)) {
                                        setLb(Number(value));
                                        setKg(calculateToKg(value));
                                    } else {
                                        setLb(0);
                                        setKg(0);
                                        alert("숫자만 입력해주세요.");
                                    }
                                }}
                            ></input>
                            <span style={{
                                marginLeft: '3px'
                            }}>lb</span>
                        </div>
                        <div>
                            <input
                                style={weightInputStyle}
                                type="text"
                                value={kg}
                                onChange={({ target: { value } }) => {
                                    if (isRightNumber(value)) {
                                        setKg(Number(value));
                                        setLb(calculateToLb(value));
                                    } else {
                                        setLb(0);
                                        setKg(0);
                                        alert("숫자만 입력해주세요.");
                                    }
                                }}
                            ></input>
                            <span style={{
                                marginLeft: '3px'
                            }}>kg</span>
                        </div>
                    </div>
                </div>

                <div style={buttonWrapperStyle}>
                    <div style={addButtonWrapperStyle}>
                        <button style={addButtonStyle}
                            onClick={() => {
                                insertMyRm(new MyRmDto(name, repetition, lb)).then((response) => {
                                    ifExpired(response, navigate);

                                    getMyRm().then((response) => {
                                        ifExpired(response, navigate);
                                        setRms(response.data);
                                    }).catch((error) => {
                                        // TODO 예외처리
                                    });

                                    setName("");
                                    setRepetition(0);
                                    setLb(0);
                                    setKg(0);

                                }).catch((error) => {
                                    // TODO 예외처리
                                });
                            }}>추가</button>
                    </div>

                    <div style={closeButtonWrapperStyle}>
                        <button style={closeButtonStyle} onClick={() => setModalIsOpen(false)}>x</button>
                    </div>
                </div>
            </Modal>
        </div>
    );
}