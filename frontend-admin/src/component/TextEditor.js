import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import React, { useState } from 'react';

export default function TextEditor({ setText, text }) {

    const [viewText, setViewText] = useState(text);

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

    return (
        <>
            <ReactQuill
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