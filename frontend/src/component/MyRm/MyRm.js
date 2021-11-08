import React, { useState } from 'react';
import styles from './MyRm.module.css';
import { getMyRm, insertMyRm } from '../../api/MyRm';
import Modal from 'react-modal';
import MyRmDto from '../../dto/MyRmDto'

export default function MyRm() {

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
                        <th>운동명</th>
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
                    position: 'absolute',
                    top: '15%',
                    height: '50%',
                    margin: 'var(--comm-margin)',
                    borderRadius: 'var(--comm-border-radius)',
                }
            }}>
                <button onClick={() => setModalIsOpen(false)}>x</button>

                <span>운동명 : </span><input 
                            type="text"
                            value={name}
                            onChange={({ target: {value} }) => {
                                setName(value)
                            }}
                        ></input>
                <span>횟수 : </span><input 
                            type="text"
                            value={repetition}
                            onChange={({ target: {value} }) => {
                                setRepetition(value)
                            }}
                        ></input>
                <span>중량 : </span><input 
                            type="text"
                            value={lb}
                            onChange={({ target: {value} }) => {
                                setLb(value)
                            }}
                        ></input>

                <button onClick={() => {
                    console.log("click");
                    insertMyRm(new MyRmDto(name, repetition, lb));
                    // console.log(insertMyRm());
                }}>추가</button>
            </Modal>
        </div>
    );
}