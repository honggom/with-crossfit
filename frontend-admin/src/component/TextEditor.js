import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import React, { useState, useEffect } from 'react';

export default function TextEditor({ setText }) {

    const [viewText, setViewText] = useState('');

    const modules = {
        toolbar: [
            [{ 'header': [1, 2, false] }],
            ['bold', 'italic', 'underline', 'strike', 'blockquote'],
            [{ 'list': 'ordered' }, { 'list': 'bullet' }, { 'indent': '-1' }, { 'indent': '+1' }],
            ['link', 'image'],
            [{ 'align': [] }, { 'color': [] }, { 'background': [] }],
            ['clean']
        ],
    }

    const formats = [
        'header',
        'bold', 'italic', 'underline', 'strike', 'blockquote',
        'list', 'bullet', 'indent',
        'link', 'image',
        'align', 'color', 'background',
    ]

    let instance;

    function getHTML() {
        console.log(instance.unprivilegedEditor.getHTML());
        return instance.unprivilegedEditor.getHTML();
    }

    return (
        <>
            <ReactQuill
                ref={(el) => {
                    instance = el;
                }}
                theme="snow"
                modules={modules}
                formats={formats}
                value={viewText}
                onChange={(value) => {
                    setViewText(value);
                    setText(value);
                }}
            />
        </>
    );

}