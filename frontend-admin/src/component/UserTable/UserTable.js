import React, { useState, useEffect } from 'react';
import { BsPen } from 'react-icons/bs';
import Modal from 'react-modal';
import { content, fontStyle, inputStyle, rowWrapper, rowWrapper2, selectStyle, addButtonStyle, closeButtonStyle } from './modalStyle';
import { getBox, getNotRegisteredUser, insertNewBoxToUser } from '../../api/pages/UserRegister';
import { useNavigate } from "react-router-dom";
import { errorHandle } from '../../util/util';
import Boxes from './Boxes/Boxes';

export default function UserTable() {

    let navigate = useNavigate();

    const [users, setUsers] = useState([]);
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [createdAt, setCreatedAt] = useState('');
    const [curBox, setCurBox] = useState('');
    const [curBoxId, setCurBoxId] = useState(0);
    const [boxes, setBoxes] = useState([]);

    useEffect(() => {
        getNotRegisteredUser().then((response) => {
            setUsers(response.data);
            getBox().then((response) => {
                setBoxes(response.data);
                if (response.data.length !== 0) {
                    setCurBox(response.data[0].name);
                    setCurBoxId(response.data[0].id);
                } 
            }).catch((error) => {
                errorHandle(error, navigate);
            });
        }).catch((error) => {
            errorHandle(error, navigate);
        });
    }, []);

    if (users !== undefined && users !== null) {
        return (
            <>
                <tbody>
                    {users.map(user => (
                        <tr key={user.email}>
                            <td>{user.email}</td>
                            <td>{user.name}</td>
                            <td>{user.createdAt}</td>
                            <td>
                                <button onClick={() => {
                                    setModalIsOpen(true);
                                    setEmail(user.email);
                                    setName(user.name);
                                    setCreatedAt(user.createdAt);
                                }}>
                                    <BsPen />
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>

                <Modal isOpen={modalIsOpen} style={{
                    overlay: {
                    },
                    content: content,
                }}>
                    <div style={rowWrapper}>
                        <span style={fontStyle}>이메일</span>
                        <input style={inputStyle} readOnly value={email} ></input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>이름</span>
                        <input style={inputStyle} readOnly value={name} ></input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>가입일자</span>
                        <input style={inputStyle} readOnly value={createdAt} ></input>
                    </div>

                    <div style={rowWrapper}>
                        <span style={fontStyle}>박스지정</span>
                        <select style={selectStyle} onChange={(e) => {
                            setCurBox(e.target[e.target.selectedIndex].innerText);
                            setCurBoxId(Number(e.target[e.target.selectedIndex].value));
                        }}>
                            <Boxes boxes={boxes} />
                        </select>
                    </div>

                    <div style={rowWrapper2}>
                        <button style={addButtonStyle}
                            onClick={() => {
                                if (window.confirm(`${name}님을 ${curBox}지점으로 등록하시겠습니까?`)) {
                                    insertNewBoxToUser(curBoxId, email).then((response) => {
                                        setModalIsOpen(false);
                                        getNotRegisteredUser().then((response) => {
                                            setUsers(response.data);
                                        }).catch((error) => {
                                            errorHandle(error, navigate);
                                        });
                                    }).catch((error) => {
                                        errorHandle(error, navigate);
                                    });
                                }
                            }}>
                            회원 등록
                        </button>
                        <button style={closeButtonStyle}
                            onClick={() => {
                                setModalIsOpen(false);
                            }}>X</button>
                    </div>
                </Modal>
            </>
        );
    } else {
        return null;
    }
}