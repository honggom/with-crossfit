import React, { useState } from 'react';
import styles from './MyRm.module.css';
import { getMyRm, insertMyRm } from '../../api/MyRm';
import Modal from 'react-modal';
import MyRmDto from '../../dto/MyRmDto'
import { useNavigate } from "react-router-dom";
import {
    wrapperStyle, fontStyle, inputStyle, buttonWrapperStyle,
    addButtonWrapperStyle, closeButtonWrapperStyle, addButtonStyle, closeButtonStyle
} from './modalStyle'
import { ifExpired } from '../../util/util';

export default function MyRm() {

    let navigate = useNavigate();

    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [name, setName] = useState("");
    const [repetition, setRepetition] = useState(0);
    const [lb, setLb] = useState(0);

    function Rm(props) {
        return (
            <tr>
                <td>{props.name}</td>
                <td>{props.repetition}</td>
                <td>{props.lb}</td>
            </tr>
        );
    }

    return (
        <div className={styles.myRm}>
            <table>
                <thead>
                    <tr>
                        <th colSpan={3} className={styles.myRmTitle}>내 RM</th>
                    </tr>
                    <tr className={styles.head}>
                        <th>운동</th>
                        <th>횟수</th>
                        <th>중량</th>
                    </tr>
                </thead>
                <tbody>
                    <tr className={styles.plusButton} onClick={() => setModalIsOpen(true)}>
                        <td colSpan={3}>+</td>
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
                                setName(value)
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
                                setRepetition(value)
                            }}
                        ></input>
                    </div>
                </div>

                <div style={wrapperStyle}>
                    <div>
                        <span style={fontStyle}>중량</span>
                    </div>
                    <div>
                        <input
                            style={inputStyle}
                            type="text"
                            value={lb}
                            onChange={({ target: { value } }) => {
                                setLb(value)
                            }}
                        ></input>
                    </div>
                </div>

                <div style={buttonWrapperStyle}>
                    <div style={addButtonWrapperStyle}>
                        <button style={addButtonStyle}
                            onClick={() => {
                                insertMyRm(new MyRmDto(name, repetition, lb)).then((response) => {
                                    ifExpired(response, navigate);
                                }).catch((error) => {
                                    // 로그인 만료된 경우 실행
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