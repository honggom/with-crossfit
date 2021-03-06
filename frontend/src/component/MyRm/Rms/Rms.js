import React, { useState } from 'react';
import { FaEdit } from "react-icons/fa";
import { BsFillCalculatorFill } from "react-icons/bs";
import Modal from 'react-modal';
import MyRmDto from '../../../dto/MyRmDto'
import { useNavigate } from "react-router-dom";
import {
    fontStyle, inputStyle, buttonWrapperStyle, addButtonWrapperStyle2, closeButtonWrapperStyle2, deleteButtonWrapperStyle,
    addButtonStyle, deleteButtonStyle, closeButtonStyle, closeButtonStyle2, content, modalWrapper, rowWrapper,
    dotLine, buttonWrapperStyle2, percentInput
} from '../modalStyle'
import { getMyRmById, updateMyRm, deleteMyRmById } from '../../../api/Rms/EditButton';
import { getMyRm } from '../../../api/MyRm';
import { errorHandle, isRightNumber, calculateToLb, calculateToKg, percentTranslate } from '../../../util/util';

export default function Rms({ rms, setRms }) {
    return (
        <>
            {rms.map(rm => (
                <tr key={rm.id}>
                    <td>{rm.name}</td>
                    <td>{rm.repetition}</td>
                    <td>{rm.lb + '/' + calculateToKg(rm.lb)}</td>
                    <td><EditButton id={rm.id} setRms={setRms} /></td>
                    <td><CalculatorButton id={rm.id} /></td>
                </tr>
            ))}
        </>
    );
}

function CalculatorButton({ id }) {

    let navigate = useNavigate();

    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [idx, setId] = useState(id);

    const [name, setName] = useState("");
    const [repetition, setRepetition] = useState(0);
    const [lb, setLb] = useState(0);
    const [kg, setKg] = useState(0);

    const [percentLb, setPercentLb] = useState(0);
    const [percentKg, setPercentKg] = useState(0);

    const [percent, setPercent] = useState(100);

    async function setPercentWeight(value) {
        setPercent(value);
        setPercentLb(percentTranslate(percent, lb));
        setPercentKg(percentTranslate(percent, calculateToKg(lb)));
    }

    return (
        <>
            <BsFillCalculatorFill onClick={() => {
                setModalIsOpen(true);

                getMyRmById(idx).then((response) => {

                    const rm = response.data;

                    setName(rm.name);
                    setRepetition(rm.repetition);
                    setLb(rm.lb);
                    setKg(calculateToKg(rm.lb));
                    setPercentLb(percentTranslate(percent, rm.lb));
                    setPercentKg(percentTranslate(percent, calculateToKg(rm.lb)));

                }).catch((error) => {
                    errorHandle(error, navigate);
                });
            }} />

            <Modal isOpen={modalIsOpen} style={{
                overlay: {
                },
                content: content,
            }}>
                <div style={modalWrapper}>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>??????</span>
                        <input style={inputStyle}
                            type='text'
                            readOnly
                            value={name}
                        >
                        </input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>??????</span>
                        <input style={inputStyle}
                            type='text'
                            readOnly
                            value={repetition}
                        >
                        </input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>??? lb</span>
                        <input style={inputStyle}
                            type='text'
                            readOnly
                            value={lb}
                        >
                        </input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>??? kg</span>
                        <input style={inputStyle}
                            type='text'
                            readOnly
                            value={calculateToKg(lb)}
                        >
                        </input>
                    </div>

                    <p style={dotLine}></p>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>% lb</span>
                        <input style={inputStyle}
                            type='text'
                            value={percentLb}
                            readOnly
                            onChange={({ target: { value } }) => {
                                setPercentLb(value);
                            }}
                        >
                        </input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>% kg</span>
                        <input style={inputStyle}
                            type='text'
                            value={percentKg}
                            readOnly
                            onChange={({ target: { value } }) => {
                                setPercentKg(value);
                            }}
                        >
                        </input>
                    </div>

                    <p style={dotLine}></p>

                    <div style={buttonWrapperStyle2}>
                        <input style={percentInput}
                            type='text'
                            value={percent}
                            onChange={({ target: { value } }) => {
                                if (isRightNumber(value)) {
                                    setPercent(value)
                                    setPercentLb(percentTranslate(value, lb));
                                    setPercentKg(percentTranslate(value, calculateToKg(lb)));
                                } else {
                                    alert("????????? ??????????????????.");
                                    setPercent(100);
                                    setPercentLb(percentTranslate(100, lb));
                                    setPercentKg(percentTranslate(100, calculateToKg(lb)));
                                }
                            }}
                        >
                        </input><span>%</span>
                        <button style={closeButtonStyle2} onClick={() => setModalIsOpen(false)}>x</button>
                    </div>
                </div>
            </Modal>
        </>
    );
}

function EditButton({ id, setRms }) {

    let navigate = useNavigate();

    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [idx, setId] = useState(id);

    const [name, setName] = useState("");
    const [repetition, setRepetition] = useState(0);
    const [lb, setLb] = useState(0);
    const [kg, setKg] = useState(0);

    return (
        <>
            <FaEdit onClick={() => {
                setModalIsOpen(true);

                getMyRmById(idx).then((response) => {
                    const rm = response.data;

                    setName(rm.name);
                    setRepetition(rm.repetition);
                    setLb(rm.lb);
                    setKg(calculateToKg(rm.lb));
                }).catch((error) => {
                    errorHandle(error, navigate);
                });

            }} />

            <Modal isOpen={modalIsOpen} style={{
                overlay: {
                },
                content: content,
            }}>
                <div style={modalWrapper}>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>??????</span>
                        <input
                            style={inputStyle}
                            type="text"
                            value={name}
                            onChange={({ target: { value } }) => {
                                setName(value);
                            }}
                        ></input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>??????</span>
                        <input
                            style={inputStyle}
                            type="text"
                            value={repetition}
                            onChange={({ target: { value } }) => {
                                if (isRightNumber(value)) {
                                    setRepetition(Number(value));
                                } else {
                                    setRepetition(0);
                                    alert("????????? ??????????????????.");
                                }
                            }}
                        ></input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>lb</span>
                        <input
                            style={inputStyle}
                            type="text"
                            value={lb}
                            onChange={({ target: { value } }) => {
                                if (isRightNumber(value)) {
                                    setLb(Number(value));
                                    setKg(calculateToKg(value));
                                } else {
                                    setLb(0);
                                    setKg(0);
                                    alert("????????? ??????????????????.");
                                }
                            }}
                        ></input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>kg</span>
                        <input
                            style={inputStyle}
                            type="text"
                            value={kg}
                            onChange={({ target: { value } }) => {
                                if (isRightNumber(value)) {
                                    setKg(Number(value));
                                    setLb(calculateToLb(value));
                                } else {
                                    setLb(0);
                                    setKg(0);
                                    alert("????????? ??????????????????.");
                                }
                            }}
                        ></input>
                    </div>

                    <p style={dotLine}></p>

                    <div style={buttonWrapperStyle}>
                        <div style={addButtonWrapperStyle2}>
                            <button style={addButtonStyle} onClick={() => {
                                const rmDto = new MyRmDto(name, repetition, lb);
                                rmDto.id = idx;

                                updateMyRm(rmDto).then((response) => {
                                    getMyRm().then((response) => {
                                        setRms(response.data);
                                        setModalIsOpen(false)
                                    }).catch((error) => {
                                        errorHandle(error, navigate);
                                    });
                                }).catch((error) => {
                                    errorHandle(error, navigate);
                                });
                            }}>??????</button>
                        </div>

                        <div style={deleteButtonWrapperStyle}>
                            <button style={deleteButtonStyle} onClick={() => {
                                deleteMyRmById(idx).then((response) => {

                                    getMyRm().then((response) => {
                                        setRms(response.data);
                                    }).catch((error) => {
                                        errorHandle(error, navigate);
                                    });
                                }).catch((error) => {
                                    errorHandle(error, navigate);
                                })
                            }}>??????</button>
                        </div>

                        <div style={closeButtonWrapperStyle2}>
                            <button style={closeButtonStyle} onClick={() => setModalIsOpen(false)}>x</button>
                        </div>
                    </div>
                </div>
            </Modal>
        </>
    );
}